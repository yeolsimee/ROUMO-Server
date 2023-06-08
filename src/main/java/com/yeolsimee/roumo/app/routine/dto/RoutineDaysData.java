package com.yeolsimee.roumo.app.routine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineDaysData {
    private String day;
    private String routineAchievement;

    public static RoutineDaysData from(String day, String routineAchievement) {
        return RoutineDaysData.builder()
                .day(day)
                .routineAchievement(routineAchievement)
                .build();
    }
}
