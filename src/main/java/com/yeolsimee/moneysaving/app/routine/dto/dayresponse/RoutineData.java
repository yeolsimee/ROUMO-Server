package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineTimeZone;
import lombok.Data;

@Data
public class RoutineData{
    private Long routineId;
    private String routineName;
    private String routineCheckYN;
    private String routineTimeZone;
    private String alarmTimeHour;
    private String alarmTimeMinute;

    @QueryProjection
    public RoutineData(Long routineId, String routineName, String routineCheckYN, RoutineTimeZone routineTimeZone, String alarmTimeHour, String alarmTimeMinute) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.routineCheckYN = routineCheckYN;
        this.routineTimeZone = routineTimeZone.routineTimeZoneId();
        this.alarmTimeHour = alarmTimeHour;
        this.alarmTimeMinute = alarmTimeMinute;
    }
}