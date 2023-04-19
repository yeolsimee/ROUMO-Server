package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutineData{
    private String routineId;
    private String routineName;
    private String routineCheckYN;
    private String routineTimeZone;
    private String alarmTimeHour;
    private String alarmTimeMinute;

    @QueryProjection
    public RoutineData(String routineId, String routineName, String routineCheckYN, String routineTimeZone, String alarmTimeHour, String alarmTimeMinute) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.routineCheckYN = routineCheckYN;
        this.routineTimeZone = routineTimeZone;
        this.alarmTimeHour = alarmTimeHour;
        this.alarmTimeMinute = alarmTimeMinute;
    }
}