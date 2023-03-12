package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.routine.entity.AlarmStatus;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineDayWeek;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoutineTest {
    private Long 사용자;
    private String 루틴_이름;
    private String 루틴_카테고리;
    private List<RoutineDayWeek> 루틴_요일 = new ArrayList<>();
    private RoutineType 루틴_공개범위;
    private AlarmStatus 루틴_알람상태;
    private String 루틴_알람시간;

    @Test
    void createRoutine() {

        사용자 = 1L;
        루틴_이름 = "코딩하기";
        루틴_카테고리 = "컴퓨터";
        루틴_요일.add(RoutineDayWeek.MONDAY);
        루틴_요일.add(RoutineDayWeek.WEDNESDAY);
        루틴_요일.add(RoutineDayWeek.SUNDAY);
        루틴_공개범위 = RoutineType.PUBLIC;
        루틴_알람상태 = AlarmStatus.ON;
        루틴_알람시간 = "12";

        Routine routine = new Routine(사용자, 루틴_이름, 루틴_카테고리, 루틴_요일, 루틴_공개범위, 루틴_알람상태, 루틴_알람시간);

        assertThat(routine.getUserId()).isEqualTo(사용자);
    }
}
