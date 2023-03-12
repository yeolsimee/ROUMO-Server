package com.yeolsimee.moneysaving.app.routine.dto;

import com.yeolsimee.moneysaving.app.routine.entity.AlarmStatus;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineDayWeek;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoutineRequest {
    private String routineName;

    private String routineCategory;

    private List<String> routineDayWeeks;
    private String routineType;
    private String alarmStatus;
    private String alarmTime;

    public static Routine toEntity(RoutineRequest routineRequest, Long userId) {
        List<RoutineDayWeek> routineDayWeekList = new ArrayList<>();
        routineRequest.getRoutineDayWeeks().stream().map(s -> routineDayWeekList.add(RoutineDayWeek.valueOf(s)));
        return Routine.builder()
                .userId(userId)
                .routineName(routineRequest.getRoutineName())
                .routineCategory(routineRequest.getRoutineCategory())
                .routineDayWeeks(routineDayWeekList)
                .routineType(RoutineType.valueOf(routineRequest.getRoutineType()))
                .alarmStatus(AlarmStatus.valueOf(routineRequest.getAlarmStatus()))
                .alarmTime(routineRequest.getAlarmTime())
                .build();
    }

}
