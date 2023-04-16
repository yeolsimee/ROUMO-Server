package com.yeolsimee.moneysaving.app.routine.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoutineDaysResponse {
    private List<RoutineDaysData> routineDays;

    public static RoutineDaysResponse from(List<RoutineDaysData> routineDays) {
        return RoutineDaysResponse.builder()
                .routineDays(routineDays)
                .build();
    }
}