package com.yeolsimee.moneysaving.app.routine.dto;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RoutineResponse {
    private Long routineId;
    private String routineName;
    private String routineCategory;
    private List<String> weekTypes;
    private String routineType;
    private String alarmStatus;
    private String alarmTime;

    public static RoutineResponse from(Routine routine) {
        List<String> weekTypes = routine.getWeekTypes()
                .stream().map(String::valueOf)
                .collect(Collectors.toList());
        return RoutineResponse.builder()
                .routineId(routine.getId())
                .routineName(routine.getRoutineName())
                .routineCategory(routine.getRoutineCategory())
                .weekTypes(weekTypes)
                .routineType(String.valueOf(routine.getRoutineType()))
                .alarmStatus(String.valueOf(routine.getAlarmStatus()))
                .alarmTime(routine.getAlarmTime())
                .build();
    }

}
