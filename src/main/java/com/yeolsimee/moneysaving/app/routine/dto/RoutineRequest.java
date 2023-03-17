package com.yeolsimee.moneysaving.app.routine.dto;

import com.yeolsimee.moneysaving.app.routine.entity.AlarmStatus;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineType;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoutineRequest {
    private String routineName;

    private String routineCategory;

    private List<String> weekTypes;
    private String routineType;
    private String alarmStatus;
    private String alarmTime;

    public RoutineRequest(String routineName, String routineCategory, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime) {
        this.routineName = routineName;
        this.routineCategory = routineCategory;
        this.weekTypes = weekTypes;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
    }

    public static Routine toEntity(RoutineRequest routineRequest, Long userId) {
        List<WeekType> weekTypes = routineRequest.getWeekTypes()
                .stream().map(WeekType::valueOf)
                .collect(Collectors.toList());
        return Routine.builder()
                .userId(userId)
                .routineName(routineRequest.getRoutineName())
                .routineCategory(routineRequest.getRoutineCategory())
                .weekTypes(weekTypes)
                .routineType(RoutineType.valueOf(routineRequest.getRoutineType()))
                .alarmStatus(AlarmStatus.valueOf(routineRequest.getAlarmStatus()))
                .alarmTime(routineRequest.getAlarmTime())
                .build();
    }

}
