package com.yeolsimee.roumo.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class RoutineSteps {

    public static ExtractableResponse<Response> 루틴_생성_요청(String uid, Map<String, Object> params) {
        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/routine")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 루틴_수정_요청(String uid, String routineId, Map<String, Object> params) {
        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/api/v1/routine/{routineId}", routineId)
                .then().log().all().extract();
    }
    public static ExtractableResponse<Response> 루틴_삭제_요청(String uid, String routineId) {
        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .when().delete("/api/v1/routine/{routineId}", routineId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 루틴_조회_요청(String uid, String routineId) {
        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .when().get("/api/v1/routine/{routineId}", routineId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 나의_루틴_전체_조회_요청(String uid, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .params(params)
                .when().get("/api/v1/routinedays")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 특정날짜의_나의_루틴_정보_조회_요청(String uid, String date, String checkedRoutineShow ) {

        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/v1/routineday?date={date}&checkedRoutineShow={checkedRoutineShow}", date, checkedRoutineShow)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 루틴_체크_하기(String uid, String routineDay, String routineCheckYN, String routineId) {
        Map<String, Object> params = new HashMap<>();
        params.put("routineCheckYN", routineCheckYN);
        params.put("routineDay", routineDay);
        params.put("routineId", routineId);

        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/routinecheck")
                .then().log().all().extract();
    }
}
