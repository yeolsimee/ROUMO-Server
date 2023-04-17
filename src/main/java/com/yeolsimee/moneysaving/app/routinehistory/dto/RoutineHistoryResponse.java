package com.yeolsimee.moneysaving.app.routinehistory.dto;

import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineHistoryResponse {
    private Long routineHistoryId;
    private Long routineId;
    private String routineDay;
    private String routineCheckYN;

    public static RoutineHistoryResponse from(RoutineHistory routineHistory) {
        return RoutineHistoryResponse.builder()
                .routineHistoryId(routineHistory.getId())
                .routineId(routineHistory.getRoutine().getId())
                .routineDay(routineHistory.getRoutineDay())
                .routineCheckYN(String.valueOf(routineHistory.getRoutineCheckYn()))
                .build();
    }
}
