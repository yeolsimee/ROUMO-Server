package com.yeolsimee.moneysaving.app.routineday.dto;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class RoutineDaysResponse {
    private List<String> routineDays;

    public static RoutineDaysResponse from(List<Routine> routines, RoutineDaysRequest routineDaysRequest) {
        List<String> routineDays = routines.stream()
                .map(routine -> routine.getRoutineDays())
                .flatMap(List::stream)
                .distinct()
                .map(routineDay -> routineDay.getRoutineDay())
                .filter(routineDay -> routineDay.compareTo(routineDaysRequest.getStartDate()) >= 0 && routineDay.compareTo(routineDaysRequest.getEndDate()) <= 0)
                .collect(Collectors.toList());

        return RoutineDaysResponse.builder()
                .routineDays(routineDays)
                .build();
    }
}
