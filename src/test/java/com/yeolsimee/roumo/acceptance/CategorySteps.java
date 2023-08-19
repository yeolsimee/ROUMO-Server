package com.yeolsimee.roumo.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class CategorySteps {
    public static ExtractableResponse<Response> 카테고리_생성_요청(String uid, String categoryName) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", categoryName);

        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/category/insert")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 카테고리_순서_변경(String uid, Long firstCategoryId, Long secondCategoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstCategoryId", firstCategoryId);
        params.put("secondCategoryId", secondCategoryId);

        return RestAssured
                .given().log().all()
                .header("x-auth", uid)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/category/order/update")
                .then().log().all().extract();
    }
}
