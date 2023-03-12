package com.yeolsimee.moneysaving.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 관련 기능")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoutineAcceptanceTest {
    public static final String ROUTINE_NAME = "코딩하기";
    public static final String ROUTINE_CATEGORY = "컴퓨터";
    public static final List<String> ROUTINE_DAY_WEEK = List.of("SUNDAY", "MONDAY");
    public static final String ROUTINE_TYPE = "PUBLIC";
    public static final String ALARM_STATUS = "ON";
    public static final String ALARM_TIME = "14";

    @DisplayName("루틴을 생성 한다.")
    @Test
    void createRoutine() {
        // when
        ExtractableResponse<Response> response = RoutineSteps.루틴_생성_요청(ROUTINE_NAME, ROUTINE_CATEGORY, ROUTINE_DAY_WEEK, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
