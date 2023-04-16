package com.yeolsimee.moneysaving.app.routinehistory.dto;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineHistory;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.Data;

@Data
public class RoutineCheckRequest {
    private String routineId;
    private String routineDay;
    private String routineCheckYN;
    public static RoutineHistory toEntity(RoutineCheckRequest routineCheckRequest, User user, Routine routine) {
        return RoutineHistory.builder()
                .user(user)
                .routine(routine)
                .routineDay(routineCheckRequest.routineDay)
                .routineCheckYn(RoutineCheckYN.valueOf(routineCheckRequest.routineCheckYN))
                .build();
    }
}
