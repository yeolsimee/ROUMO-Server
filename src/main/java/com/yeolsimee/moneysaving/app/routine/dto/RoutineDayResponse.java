package com.yeolsimee.moneysaving.app.routine.dto;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineDayResponse {
    private String routineDay;
    private String routineName;
    private String routineCheckYn;

    public static RoutineDayResponse from(Routine routine, RoutineDay routineDay) {
        return RoutineDayResponse.builder()
                .routineDay(routineDay.getRoutineDay())
                .routineName(routine.getRoutineName())
                .routineCheckYn(String.valueOf(routineDay.getRoutineCheckYn()))
                .build();
    }
}
