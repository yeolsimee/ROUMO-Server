package com.yeolsimee.moneysaving.app.routine.entity;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoutineTimeZone {
    ALL_DAY("1"),
    ANY_TIME("2"),
    MORNING("3"),
    AFTERNOON("4"),
    EVENING("5"),
    NIGHT("6"),
    BEFORE_BED_TIME("7"),
    AFTER_BED_TIME("8"),
    AM("9"),
    PM("10");
    private String routineTimeZoneId;

    RoutineTimeZone(String routineTimeZoneId) {
        this.routineTimeZoneId = routineTimeZoneId;
    }
    public String routineTimeZoneId() {
        return routineTimeZoneId;
    }
    private static final Map<String, RoutineTimeZone> BY_ROUTINE_TIME_ZONE_ID =
            Stream.of(values()).collect(Collectors.toMap(RoutineTimeZone::routineTimeZoneId, e -> e));

    public static RoutineTimeZone idOfRoutineTimeZone(String routineTimeZone) {
        return BY_ROUTINE_TIME_ZONE_ID.get(routineTimeZone);
    }
}
