package com.yeolsimee.moneysaving.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutineSteps {

    public static ExtractableResponse<Response> 루틴_생성_요청(String routineName, String categoryName, List<String> weekTypes, String routineType, String alarmStatus, String alarmTime, String routineTimeZone) {
        Map<String, Object> params = new HashMap<>();
        params.put("routineName", routineName);
        params.put("categoryName", categoryName);
        params.put("weekTypes", weekTypes);
        params.put("routineType", routineType);
        params.put("alarmStatus", alarmStatus);
        params.put("alarmTime", alarmTime);
        params.put("routineTimeZone", routineTimeZone);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/routine")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 나의_루틴_전체_조회_요청(String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().get("/api/v1/routinedays")
                .then().log().all().extract();
    }
}
