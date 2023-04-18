package com.yeolsimee.moneysaving.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yeolsimee.moneysaving.acceptance.CategorySteps.카테고리_생성_요청;
import static com.yeolsimee.moneysaving.acceptance.RoutineSteps.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("루틴 관련 기능")
public class RoutineAcceptanceTest extends AcceptanceTest{
    public static final String EMAIL = "kim7@gmail.com";
    public static final String UID = "uid1";
    public static final String ROUTINE_NAME = "코딩하기";
    public static final String ROUTINE_CATEGORY_ID = "1";
    public static final List<String> WEEK_TYPES = List.of("SUNDAY", "MONDAY");
    public static final List<String> EMPTY_WEEK_TYPES = List.of();
    public static final String ROUTINE_TYPE = "PUBLIC";
    public static final String ALARM_STATUS = "ON";
    public static final String ALARM_TIME = "1422";
    public static final String START_DATE = "20230320";
    public static final String END_DATE = "20231016";
    public static final String ROUTINE_TIME_ZONE = "1";
    public static final String PICKDAY = "20231224";

    @BeforeEach
    public void setUp() {
        super.setUp();
        카테고리_생성_요청(UID, "컴퓨터하기");
    }
    @DisplayName("루틴을 생성 한다.")
    @Test
    void createRoutine() {
        // when
        ExtractableResponse<Response> response = 루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineName")).isEqualTo(ROUTINE_NAME)
        );
    }

    @DisplayName("루틴을 수정 한다.")
    @Test
    void updateRoutine() {
        // when
        ExtractableResponse<Response> createResponse = 루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));
        String routineId = createResponse.jsonPath().getString("data.routineId");

        ExtractableResponse<Response> response = 루틴_수정_요청(UID, routineId, createRoutineUpdateParams("수정된_루틴_이름", "1", List.of("MONDAY", "SUNDAY"), "PUBLIC", "ON", "12", "3"));
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineName")).isEqualTo("수정된_루틴_이름")
        );
    }

    @DisplayName("루틴을 삭제 한다.")
    @Test
    void deleteRoutine() {
        // when
        ExtractableResponse<Response> createResponse = 루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));
        String routineId = createResponse.jsonPath().getString("data.routineId");

        루틴_삭제_요청(UID, routineId);

        ExtractableResponse<Response> response = 루틴_조회_요청(UID, routineId);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("code")).isEqualTo("400")

        );
    }

    @DisplayName("특정 기간동안의 나의 루틴 날짜 전체 조회하기")
    @Test
    void findAllMyRoutineDays() {
        // when
        루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));

        ExtractableResponse<Response> response = 나의_루틴_전체_조회_요청(UID, START_DATE, END_DATE);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getList("data.routineDays.day")).contains("20231016", "20231015")
        );
    }

    @DisplayName("특정 날짜의 나의 루틴 정보 조회하기")
    @Test
    void findMyRoutineInformation() {
        // when
        루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));

        ExtractableResponse<Response> response = 특정날짜의_나의_루틴_정보_조회_요청(UID, PICKDAY);
        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineDay")).isEqualTo(PICKDAY),
                () -> assertThat(response.jsonPath().getString("data.categoryDatas.categoryId")).contains(ROUTINE_CATEGORY_ID),
                () -> assertThat(response.jsonPath().getString("data.categoryDatas.routineDatas.routineName")).contains(ROUTINE_NAME)
        );
    }

    @DisplayName("루틴 체크 하기")
    @Test
    void checkMyRoutine() {
        // when
        루틴_생성_요청(UID, createRoutineCreateParams(ROUTINE_NAME, ROUTINE_CATEGORY_ID, WEEK_TYPES, ROUTINE_TYPE, ALARM_STATUS, ALARM_TIME, ROUTINE_TIME_ZONE));

        ExtractableResponse<Response> responseRoutineInformation = 특정날짜의_나의_루틴_정보_조회_요청(UID, PICKDAY);
        String routineDayId = responseRoutineInformation.jsonPath().getString("data.categoryDatas.routineDatas.routineDayId").replace("[", "").replace("]", "");

        // then
        ExtractableResponse<Response> response = 루틴_체크_하기(UID, routineDayId, "Y");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.jsonPath().getString("data.routineCheckYN")).isEqualTo("Y"),
                () -> assertThat(response.jsonPath().getString("data.routineDayId")).contains(routineDayId)
        );
    }

    private Map<String, Object> createRoutineCreateParams(String routineName, String categoryId, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime, String routineTimeZone) {
        Map<String, Object> params = new HashMap<>();
        params.put("routineName", routineName);
        params.put("categoryId", categoryId);
        params.put("weekTypes", weekTypes);
        params.put("routineType", routineType);
        params.put("alarmStatus", alarmStatus);
        params.put("alarmTime", alarmTime);
        params.put("routineTimeZone", routineTimeZone);
        return params;
    }

    private Map<String, Object> createRoutineUpdateParams(String routineName, String categoryId, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime, String routineTimeZone) {
        Map<String, Object> params = new HashMap<>();
        params.put("routineName", routineName);
        params.put("categoryId", categoryId);
        params.put("weekTypes", weekTypes);
        params.put("routineType", routineType);
        params.put("alarmStatus", alarmStatus);
        params.put("alarmTime", alarmTime);
        params.put("routineTimeZone", routineTimeZone);
        return params;
    }
}
