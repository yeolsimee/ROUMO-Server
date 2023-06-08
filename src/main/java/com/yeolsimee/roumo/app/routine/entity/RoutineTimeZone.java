package com.yeolsimee.roumo.app.routine.entity;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoutineTimeZone {
    ALL_DAY("1"),
    ANY_TIME("2"),
    AFTER_BED_TIME("3"),
    MORNING("4"),
    AM("5"),
    AFTERNOON("6"),
    PM("7"),
    EVENING("8"),
    NIGHT("9"),
    BEFORE_BED_TIME("10");
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
