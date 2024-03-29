package com.yeolsimee.roumo.app.routine.service;

import com.yeolsimee.roumo.app.category.entity.Category;
import com.yeolsimee.roumo.app.category.repository.CategoryRepository;
import com.yeolsimee.roumo.app.common.exception.BaseException;
import com.yeolsimee.roumo.app.common.exception.EntityNotFoundException;
import com.yeolsimee.roumo.app.common.response.ResponseMessage;
import com.yeolsimee.roumo.app.routine.dto.RoutineDaysData;
import com.yeolsimee.roumo.app.routine.dto.RoutineDaysResponse;
import com.yeolsimee.roumo.app.routine.dto.RoutineRequest;
import com.yeolsimee.roumo.app.routine.dto.RoutineResponse;
import com.yeolsimee.roumo.app.routine.dto.dayresponse.DayResponse;
import com.yeolsimee.roumo.app.routine.entity.Routine;
import com.yeolsimee.roumo.app.routine.entity.WeekType;
import com.yeolsimee.roumo.app.routine.repository.RoutineRepository;
import com.yeolsimee.roumo.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.roumo.app.routinehistory.entity.RoutineHistory;
import com.yeolsimee.roumo.app.routinehistory.repository.RoutineHistoryRepository;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.service.UserService;
import com.yeolsimee.roumo.util.RoutineUtils;
import com.yeolsimee.roumo.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineHistoryRepository routineHistoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;


    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        User user = userService.getUserByUserId(userId);
        Category category = categoryRepository.findByIdAndUserId(Long.parseLong(routineRequest.getCategoryId()), userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String routineEndDate = "99999999";
        if (routineRequest.getWeekTypes().isEmpty()) {
            routineEndDate = today;
        }
        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user, "N", today, routineEndDate));
        return RoutineResponse.from(routine);
    }

    @Transactional
    public RoutineResponse updateRoutine(RoutineRequest routineRequest, Long userId, Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        RoutineCheckYN routineCheckYn = RoutineCheckYN.N;

        Optional<RoutineHistory> routineHistoryOptional = routineHistoryRepository.findRoutineHistory(userId, routineId, today);
        if (routineHistoryOptional.isPresent()) {
            routineCheckYn = routineHistoryOptional.get().getRoutineCheckYn();
        }

        deleteRoutineOrChangeEndDate(routine, today);

        User user = userService.getUserByUserId(userId);
        Category category = categoryRepository.findByIdAndUserId(Long.parseLong(routineRequest.getCategoryId()), userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
        String routineEndDate = "99999999";

        if (routineRequest.getWeekTypes().isEmpty()) {
            routineEndDate = today;
        }

        Routine newRoutine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user, "N", today, routineEndDate));

        RoutineHistory routineHistory = RoutineHistory.builder()
                .routine(newRoutine)
                .routineDay(today)
                .user(user)
                .routineCheckYn(routineCheckYn)
                .build();

        routineHistoryRepository.save(routineHistory);

        return RoutineResponse.from(newRoutine);
    }

    public void deleteRoutineOrChangeEndDate(Routine routine, String today) {
        if (routine.getRoutineStartDate().equals(today)) {
            routineHistoryRepository.deleteByRoutineId(routine.getId());
            routineRepository.deleteById(routine.getId());
        } else {
            String yesterday = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            routine.changeEndDate(yesterday);
            routine.changeRoutineDeleteYN("Y");
        }
    }

    @Transactional
    public void deleteRoutine(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        deleteRoutineOrChangeEndDate(routine, today);
    }

    public RoutineResponse findRoutineByUserIdAndRoutineId(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        return RoutineResponse.from(routine);
    }

    public RoutineDaysResponse findRoutineDays(Long userId, String startDate, String endDate) {
        List<String> dayList = TimeUtils.makeDateList(startDate, endDate);

        List<RoutineDaysData> routineDaysDatas = new ArrayList<>();

        for (String routineDay : dayList) {
            try {
                WeekType week = TimeUtils.convertDayToWeekType(routineDay);
                String routineAchievement = findDayRoutineAchievement(userId, routineDay, week, "Y");
                RoutineDaysData routineDaysData = RoutineDaysData.from(routineDay, routineAchievement);
                routineDaysDatas.add(routineDaysData);
            } catch (ParseException e) {
                throw new BaseException(ResponseMessage.NOT_PARSE_WEEKTYPE);
            }
        }
        return RoutineDaysResponse.from(routineDaysDatas);
    }

    public DayResponse findRoutineDay(Long userId, String date, String checkedRoutineShow) {
        try {
            WeekType weekType = TimeUtils.convertDayToWeekType(date);
            return routineRepository.findRoutineDay(userId, date, weekType, checkedRoutineShow);
        } catch (ParseException e) {
            throw new BaseException(ResponseMessage.NOT_PARSE_WEEKTYPE);
        }
    }

    public Routine findRoutineByRoutineIdAndUserId(Long routineId, Long userId) {
        return routineRepository.findByIdAndUserId(routineId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
    }

    private String findDayRoutineAchievement(Long userId, String routineDay, WeekType weekType, String routineCheckYN) {
        String routineAchievement = "NONE";
        double dayRoutineNum = findDayRoutineNum(userId, routineDay, weekType);
        double dayCheckedRoutineNum = routineHistoryRepository.findDayCheckedRoutineNum(userId, routineDay, RoutineCheckYN.valueOf(routineCheckYN));
        if (dayRoutineNum != 0) {
            String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (routineDay.compareTo(today) > 0) {
                routineAchievement = "YET";
            } else {
                double routineAchievementRate = dayCheckedRoutineNum / dayRoutineNum;
                routineAchievement = RoutineUtils.convertRoutineAchievementRateToRoutineAchievement(routineAchievementRate);
            }
        }
        return routineAchievement;
    }

    private Integer findDayRoutineNum(Long userId, String routineDay, WeekType weekType) {
        return routineRepository.findDayRoutineNum(userId, routineDay, weekType);
    }

    public List<Routine> findRoutineByCategoryId(Long categoryId) {
        return routineRepository.findByRoutineByCategoryId(categoryId);
    }

    public List<RoutineResponse> findRoutinesByUserId(Long userId) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Routine> routines = routineRepository.findByUserId(userId, today);
        return RoutineResponse.fromRoutines(routines);
    }
}
