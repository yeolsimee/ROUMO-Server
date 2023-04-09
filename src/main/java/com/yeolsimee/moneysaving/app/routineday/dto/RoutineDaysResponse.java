package com.yeolsimee.moneysaving.app.routineday.dto;

import com.yeolsimee.moneysaving.app.routineday.entity.RoutineCheckYN;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Data
@Builder
public class RoutineDaysResponse {
    private List<RoutineDaysResponseData> routineDays;

    public static RoutineDaysResponse from(List<RoutineDay> routineDays) {

        Map<String, long[]> map = routineDays.stream()
                .collect(Collectors.groupingBy(RoutineDay::getRoutineDay, Collectors.reducing(new long[]{0, 0},
                        rd -> new long[]{rd.getRoutineCheckYn() == RoutineCheckYN.Y ? 1 : 0, 1},
                        (a, b) -> new long[]{a[0] + b[0], a[1] + b[1]})));

        Map<String, long[]> sortedMap = new TreeMap<>(map);

        List<RoutineDaysResponseData> results = sortedMap.entrySet().stream()
                .map(entry -> new RoutineDaysResponseData(entry.getKey(), entry.getValue()[0] * 100.0 / entry.getValue()[1]))
                .collect(Collectors.toList());

        return RoutineDaysResponse.builder()
                .routineDays(results)
                .build();
    }
}

@Data
@Builder
class RoutineDaysResponseData {
    private String day;
    private Double routineAchievementRate;
}