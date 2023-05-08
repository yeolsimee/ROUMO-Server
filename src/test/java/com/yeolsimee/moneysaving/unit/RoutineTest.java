package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.*;
import com.yeolsimee.moneysaving.app.user.entity.Role;
import com.yeolsimee.moneysaving.app.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 단위 테스트")
class RoutineTest {
    private User 사용자;
    private String 루틴_이름;
    private Category 루틴_카테고리;
    private List<WeekType> 루틴_요일 = new ArrayList<>();
    private RoutineType 루틴_공개범위;
    private AlarmStatus 루틴_알람상태;
    private String 루틴_알람시간;
    private RoutineTimeZone 루틴_시간대;
    private String 루틴_시작날짜;
    private String 루틴_종료날짜;

    @Test
    @DisplayName("루틴 생성하기")
    void createRoutine() {

        사용자 = new User("name", "username", Role.ROLE_USER);

        루틴_이름 = "코딩하기";
        루틴_카테고리 = new Category("컴퓨터");
        루틴_요일.add(WeekType.MONDAY);
        루틴_요일.add(WeekType.WEDNESDAY);
        루틴_요일.add(WeekType.SUNDAY);
        루틴_공개범위 = RoutineType.PUBLIC;
        루틴_알람상태 = AlarmStatus.ON;
        루틴_알람시간 = "12";
        루틴_시간대 = RoutineTimeZone.AM;
        루틴_시작날짜 = "20230426";
        루틴_종료날짜 = "";

        Routine routine = new Routine(사용자, 루틴_이름, 루틴_카테고리, 루틴_요일, 루틴_공개범위, 루틴_알람상태, 루틴_알람시간, 루틴_시작날짜, 루틴_종료날짜, 루틴_시간대, "N");

        assertThat(routine.getUser()).isEqualTo(사용자);
    }
}
