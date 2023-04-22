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
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.CategoryData;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.DayResponse;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.RoutineData;
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
        Category category = categoryService.findCategoryById(routineRequest.getCategoryId());
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
        Category category = categoryService.findCategoryById(routineRequest.getCategoryId());
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

    public DayResponse findRoutineDay(Long userId, String routineDay) {
        try {
            WeekType weekType = TimeUtils.convertDayToWeekType(routineDay);
            return routineRepository.findRoutineDay(userId, routineDay, weekType);
        } catch (ParseException e) {
            throw new BaseException(ResponseMessage.NOT_PARSE_WEEKTYPE);
        }
    }

    public Routine findRoutineByRoutineId(Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        return routine;
    }

    private String findDayRoutineAchievement(Long userId, String routineDay, WeekType weekType, String routineCheckYN) {
        String routineAchievement = "NONE";
        double dayRoutineNum = findDayRoutineNum(userId, routineDay, weekType);
        double dayCheckedRoutineNum = routineHistoryRepository.findDayCheckedRoutineNum(userId, routineDay, RoutineCheckYN.valueOf(routineCheckYN));
        if (dayRoutineNum != 0) {
            double routineAchievementRate = dayCheckedRoutineNum / dayRoutineNum;
            routineAchievement = RoutineUtils.convertRoutineAchievementRateToRoutineAchievement(routineAchievementRate);
        }
        return routineAchievement;
    }

    private Integer findDayRoutineNum(Long userId, String routineDay, WeekType weekType) {
        return routineRepository.findDayRoutineNum(userId, routineDay, weekType);
    }
}
