package com.yeolsimee.roumo.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yeolsimee.roumo.acceptance.CategorySteps.카테고리_생성_요청;
import static com.yeolsimee.roumo.acceptance.CategorySteps.카테고리_순서_변경;
import static com.yeolsimee.roumo.acceptance.RoutineSteps.루틴_생성_요청;
import static com.yeolsimee.roumo.acceptance.RoutineSteps.특정날짜의_나의_루틴_정보_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("카테고리 관련 기능")
public class CategoryAcceptanceTest extends AcceptanceTest {

	public static final String UID = "a1";

	@BeforeEach
	public void setUp() {
		super.setUp();
	}

	@DisplayName("카테고리 순서 변경해서 카테고리의 순서대로 루틴이 나오게 합니다.")
	@Test
	void updateCategoryOrder() {
		// given
		ExtractableResponse<Response> categoryResponse1 = 카테고리_생성_요청(UID, "카테고리1");
		ExtractableResponse<Response> categoryResponse2 = 카테고리_생성_요청(UID, "카테고리2");

		String categoryId1 = categoryResponse1.jsonPath().getString("data.categoryId");
		루틴_생성_요청(UID, createRoutineCreateParams("루틴 1", categoryId1, List.of("SUNDAY", "MONDAY"), "PUBLIC", "ON", "1422", "1"));
		루틴_생성_요청(UID, createRoutineCreateParams("루틴 2", categoryId1, List.of("SUNDAY", "MONDAY"), "PUBLIC", "ON", "1422", "1"));

		String categoryId2 = categoryResponse2.jsonPath().getString("data.categoryId");
		루틴_생성_요청(UID, createRoutineCreateParams("루틴 3", categoryId2, List.of("SUNDAY", "MONDAY"), "PUBLIC", "ON", "1422", "1"));
		루틴_생성_요청(UID, createRoutineCreateParams("루틴 4", categoryId2, List.of("SUNDAY", "MONDAY"), "PUBLIC", "ON", "1422", "1"));
		카테고리_순서_변경(UID, Long.parseLong(categoryId1), Long.parseLong(categoryId2));

		//when

		ExtractableResponse<Response> response = 특정날짜의_나의_루틴_정보_조회_요청(UID, "20301124", "Y");
		//then
		assertAll(
				() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
				() -> assertThat(response.jsonPath().getString("data.routineDay")).isEqualTo("20301124"),
				() -> assertThat(response.jsonPath().getString("data.categoryDatas[0].categoryId")).isEqualTo("2"),
				() -> assertThat(response.jsonPath().getString("data.categoryDatas[0].categoryOrder")).isEqualTo("1"),
				() -> assertThat(response.jsonPath().getString("data.categoryDatas[1].categoryId")).isEqualTo("1"),
				() -> assertThat(response.jsonPath().getString("data.categoryDatas[1].categoryOrder")).isEqualTo("2")
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
}
