package com.yeolsimee.moneysaving.acceptance;

import com.yeolsimee.moneysaving.app.user.entity.User;
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
                .header("uid", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/routine")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 나의_루틴_전체_조회_요청(String uid, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        return RestAssured
                .given().log().all()
                .header("uid",uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().get("/api/v1/routinedays")
                .then().log().all().extract();
    }
}
