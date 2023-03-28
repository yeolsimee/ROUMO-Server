package com.yeolsimee.moneysaving.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yeolsimee.moneysaving.acceptance.RoutineSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("루틴 관련 기능")
public class RoutineAcceptanceTest extends AcceptanceTest{
    public static final String EMAIL = "kim7@gmail.com";
    public static final String UID = "uid1";
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
    public static final String PICKDAY = "20231224";

    @BeforeEach
    public void setUp() {
        super.setUp();
    }
    @DisplayName("루틴을 생성 한다.")
    @Test
    void createRoutine() {
        // when
        ExtractableResponse<Response> response = 루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineName")).isEqualTo(ROUTINE_NAME)
        );
    }

    @DisplayName("특정 기간동안의 나의 루틴 날짜 전체 조회하기")
    @Test
    void findAllMyRoutineDays() {
        // when
        루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));

        ExtractableResponse<Response> response = 나의_루틴_전체_조회_요청(UID, START_DATE, END_DATE);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("data.routineDays")).contains("20231016", "20231015")
        );
    }

    @DisplayName("특정 날짜의 나의 루틴 정보 조회하기")
    @Test
    void findMyRoutineInformation() {
        // when
        루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));

        ExtractableResponse<Response> response = 특정날짜의_나의_루틴_정보_조회_요청(UID, PICKDAY);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineDay")).isEqualTo(PICKDAY),
                () -> assertThat(response.jsonPath().getString("data.categoryDatas.categoryName")).contains(ROUTINE_CATEGORY),
                () -> assertThat(response.jsonPath().getString("data.categoryDatas.routineDatas.routineName")).contains(ROUTINE_NAME)
        );
    }

    private Map<String, Object> createRoutineCreateParams(String routineName, String categoryName, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime, String routineTimeZone) {
        Map<String, Object> params = new HashMap<>();
        params.put("routineName", routineName);
        params.put("categoryName", categoryName);
        params.put("weekTypes", weekTypes);
        params.put("routineType", routineType);
        params.put("alarmStatus", alarmStatus);
        params.put("alarmTime", alarmTime);
        params.put("routineTimeZone", routineTimeZone);
        return params;
    }
}
