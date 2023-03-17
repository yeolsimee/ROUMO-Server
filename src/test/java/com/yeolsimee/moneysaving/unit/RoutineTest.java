package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.routine.entity.AlarmStatus;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 단위 테스트")
class RoutineTest {
    private Long 사용자;
    private String 루틴_이름;
    private String 루틴_카테고리;
    private List<WeekType> 루틴_요일 = new ArrayList<>();
    private RoutineType 루틴_공개범위;
    private AlarmStatus 루틴_알람상태;
    private String 루틴_알람시간;

    @Test
    @DisplayName("루틴 생성하기")
    void createRoutine() {

        사용자 = 1L;
        루틴_이름 = "코딩하기";
        루틴_카테고리 = "컴퓨터";
        루틴_요일.add(WeekType.MONDAY);
        루틴_요일.add(WeekType.WEDNESDAY);
        루틴_요일.add(WeekType.SUNDAY);
        루틴_공개범위 = RoutineType.PUBLIC;
        루틴_알람상태 = AlarmStatus.ON;
        루틴_알람시간 = "12";

        Routine routine = new Routine(사용자, 루틴_이름, 루틴_카테고리, 루틴_요일, 루틴_공개범위, 루틴_알람상태, 루틴_알람시간);

        assertThat(routine.getUserId()).isEqualTo(사용자);
    }
}
