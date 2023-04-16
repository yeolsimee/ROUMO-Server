package com.yeolsimee.moneysaving.app.routinehistory.dto;

import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineHistoryResponse {
    private String routineHistoryId;
    private String routineId;
    private String routineDay;
    private String routineCheckYN;

    public static RoutineHistoryResponse from(RoutineHistory routineHistory) {
        return RoutineHistoryResponse.builder()
                .routineHistoryId(String.valueOf(routineHistory.getId()))
                .routineId(String.valueOf(routineHistory.getRoutine().getId()))
                .routineDay(routineHistory.getRoutineDay())
                .routineCheckYN(String.valueOf(routineHistory.getRoutineCheckYn()))
                .build();
    }
}
