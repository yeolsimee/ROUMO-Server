package com.yeolsimee.moneysaving.app.routine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineDaysResponse {
    private String day;
    private Double routineAchievementRate;

    public static RoutineDaysResponse from(String day, Double routineAchievementRate) {
        return RoutineDaysResponse.builder()
                .day(day)
                .routineAchievementRate(routineAchievementRate)
                .build();
    }
}