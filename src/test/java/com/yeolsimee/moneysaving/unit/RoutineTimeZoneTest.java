package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.routine.entity.RoutineTimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 시간대")
public class RoutineTimeZoneTest {
        private String 시간대;
    @Test
    void createRoutineTimeZone() {
        시간대 = "1";
        RoutineTimeZone routineTimeZone = RoutineTimeZone.idOfRoutineTimeZone(시간대);
        assertThat(routineTimeZone).isEqualTo(RoutineTimeZone.ALL_DAY);
    }
}
