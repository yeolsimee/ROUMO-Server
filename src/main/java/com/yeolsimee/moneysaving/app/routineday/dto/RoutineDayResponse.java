package com.yeolsimee.moneysaving.app.routineday.dto;

import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineDayResponse {
    private String routineDayId;
    private String routineDay;
    private String routineCheckYN;

    public static RoutineDayResponse from(RoutineDay routineDay) {
        return RoutineDayResponse.builder()
                .routineDayId(String.valueOf(routineDay.getId()))
                .routineDay(routineDay.getRoutineDay())
                .routineCheckYN(String.valueOf(routineDay.getRoutineCheckYn()))
                .build();
    }
}
