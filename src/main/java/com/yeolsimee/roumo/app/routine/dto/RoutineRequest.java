package com.yeolsimee.roumo.app.routine.dto;

import com.yeolsimee.roumo.app.category.entity.Category;
import com.yeolsimee.roumo.app.routine.entity.*;
import com.yeolsimee.roumo.app.user.entity.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoutineRequest {
    private String routineName;
    private String categoryId;
    private List<String> weekTypes;
    private String routineType;
    private String alarmStatus;
    private String alarmTime;
    private String routineTimeZone;

    public RoutineRequest(String routineName, String categoryId, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime, String routineTimeZone) {
        this.routineName = routineName;
        this.categoryId = categoryId;
        this.weekTypes = weekTypes;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
        this.routineTimeZone = routineTimeZone;
    }

    public static Routine toEntity(RoutineRequest routineRequest, Category category, User user, String routineDeleteYN, String routineStartDate, String routineEndDate) {
        List<WeekType> weekTypes = routineRequest.getWeekTypes()
                .stream().map(WeekType::valueOf)
                .collect(Collectors.toList());
        return Routine.builder()
                .user(user)
                .routineName(routineRequest.getRoutineName())
                .category(category)
                .weekTypes(weekTypes)
                .routineType(RoutineType.valueOf(routineRequest.getRoutineType()))
                .alarmStatus(AlarmStatus.valueOf(routineRequest.getAlarmStatus()))
                .alarmTime(routineRequest.getAlarmTime())
                .routineTimeZone(RoutineTimeZone.idOfRoutineTimeZone(routineRequest.getRoutineTimeZone()))
                .routineDeleteYN(routineDeleteYN)
                .routineStartDate(routineStartDate)
                .routineEndDate(routineEndDate)
                .build();
    }

}
