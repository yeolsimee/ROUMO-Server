package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.category.service.CategoryService;
import com.yeolsimee.moneysaving.app.common.exception.BaseException;
import com.yeolsimee.moneysaving.app.common.exception.EntityNotFoundException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineDaysData;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineDaysResponse;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.moneysaving.app.routinehistory.repository.RoutineHistoryRepository;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.service.UserService;
import com.yeolsimee.moneysaving.util.RoutineUtils;
import com.yeolsimee.moneysaving.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineHistoryRepository routineHistoryRepository;
    private final CategoryService categoryService;
    private final UserService userService;


    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        User user = userService.getUserByUserId(userId);
        Category category = categoryService.findCategoryById(Long.valueOf(routineRequest.getCategoryId()));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user, "N", today, "99999999"));
        return RoutineResponse.from(routine);
    }

    @Transactional
    public RoutineResponse updateRoutine(RoutineRequest routineRequest, Long userId, Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yesterday = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (routine.getRoutineStartDate().equals(today)) {
            routineRepository.delete(routine);
        } else {
            routine.changeEndDate(yesterday);
        }

        User user = userService.getUserByUserId(userId);
        Category category = categoryService.findCategoryById(Long.valueOf(routineRequest.getCategoryId()));
        Routine newRoutine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user, "N", today, ""));
        return RoutineResponse.from(newRoutine);
    }

    @Transactional
    public void deleteRoutine(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yesterday = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (routine.getRoutineStartDate().equals(today)) {
            routineRepository.delete(routine);
        } else {
            routine.changeEndDate(yesterday);
            routine.changeRoutineDeleteYN("Y");
        }
    }

    public RoutineResponse findRoutineByUserIdAndRoutineId(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        return RoutineResponse.from(routine);
    }

    public RoutineDaysResponse findRoutineDays(Long userId, String startDate, String endDate) {
        List<String> dayList = TimeUtils.makeDateList(startDate, endDate);

        List<RoutineDaysData> routineDaysDatas = new ArrayList<>();

        for (String day : dayList) {
            WeekType week;
            try {
                week = TimeUtils.convertDayToWeekType(day);
            } catch (ParseException e) {
                throw new BaseException(ResponseMessage.NOT_PARSE_WEEKTYPE);
            }
            String routineAchievement = findDayRoutineAchievement(userId, day, week, "Y");
            RoutineDaysData routineDaysData = RoutineDaysData.from(day, routineAchievement);
            routineDaysDatas.add(routineDaysData);
        }
        return RoutineDaysResponse.from(routineDaysDatas);
    }

    public Routine findRoutineByRoutineId(Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        return routine;
    }

    public Integer findDayRoutineNum(Long userId, String day, WeekType weekType) {
        return routineRepository.findDayRoutineNum(userId, day, weekType);
    }

    public String findDayRoutineAchievement(Long userId, String day, WeekType weekType, String routineCheckYN) {
        String routineAchievement = "NONE";
        double dayRoutineNum = findDayRoutineNum(userId, day, weekType);
        double dayCheckedRoutineNum = routineHistoryRepository.findDayCheckedRoutineNum(userId, day, RoutineCheckYN.valueOf(routineCheckYN));
        if (dayRoutineNum != 0) {
            double routineAchievementRate = dayCheckedRoutineNum / dayRoutineNum;
            routineAchievement = RoutineUtils.convertRoutineAchievementRateToRoutineAchievement(routineAchievementRate);
        }
        return routineAchievement;
    }
}
