package com.yeolsimee.moneysaving.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 관련 기능")
public class RoutineAcceptanceTest extends AcceptanceTest{
    public static final String ROUTINE_NAME = "코딩하기";
    public static final String ROUTINE_CATEGORY = "컴퓨터";
    public static final List<String> WEEK_TYPES = List.of("SUNDAY", "MONDAY");
    public static final List<String> EMPTY_WEEK_TYPES = List.of();
    public static final String ROUTINE_TYPE = "PUBLIC";
    public static final String ALARM_STATUS = "ON";
    public static final String ALARM_TIME = "14";
    public static final String START_DATE = "20230320";
    public static final String END_DATE = "20231016";
    public static final String ROUTINE_TIME_ZONE = "1";

    @DisplayName("루틴을 생성 한다.")
    @Test
    void createRoutine() {
        // when
        ExtractableResponse<Response> response = RoutineSteps.루틴_생성_요청(ROUTINE_NAME, ROUTINE_CATEGORY, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("특정 기간동안의 나의 루틴 날짜 전체 조회하기")
    @Test
    void findAllMyRoutineDays() {
        // when
        RoutineSteps.루틴_생성_요청(ROUTINE_NAME, ROUTINE_CATEGORY, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE);

        ExtractableResponse<Response> response = RoutineSteps.나의_루틴_전체_조회_요청(START_DATE, END_DATE);
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
