package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("루틴 서비스 테스트")
@SpringBootTest
@Transactional
public class RoutineServiceTest {
    private Long 사용자;
    private String 루틴_이름;
    private String 루틴_카테고리;
    private List<String> 루틴_요일 = new ArrayList<>();
    private String 루틴_공개범위;
    private String 루틴_알람상태;
    private String 루틴_알람시간;
    @Autowired
    RoutineService routineService;

    @Test
    public void routineCreate() {
        //given
        사용자 = 1L;
        루틴_이름 = "코딩하기";
        루틴_카테고리 = "컴퓨터";
        루틴_요일.add("MONDAY");
        루틴_요일.add("WEDNESDAY");
        루틴_요일.add("SUNDAY");
        루틴_공개범위 = "PUBLIC";
        루틴_알람상태 = "ON";
        루틴_알람시간 = "12";

        RoutineRequest routineRequest = new RoutineRequest(루틴_이름, 루틴_카테고리, 루틴_요일, 루틴_공개범위, 루틴_알람상태, 루틴_알람시간);

        //when
        RoutineResponse routineResponse = routineService.createRoutine(routineRequest, 사용자);

        //then
        assertThat(routineResponse).isNotNull();
        assertThat(routineResponse.getRoutineName()).isEqualTo("코딩하기");
        assertThat(routineResponse.getWeekTypes()).contains("MONDAY", "WEDNESDAY", "SUNDAY");
    }
}
