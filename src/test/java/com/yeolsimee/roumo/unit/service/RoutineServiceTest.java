package com.yeolsimee.roumo.unit.service;

import com.yeolsimee.roumo.app.category.dto.CategoryRequest;
import com.yeolsimee.roumo.app.category.dto.CategoryResponse;
import com.yeolsimee.roumo.app.category.service.CategoryService;
import com.yeolsimee.roumo.app.routine.dto.RoutineRequest;
import com.yeolsimee.roumo.app.routine.dto.RoutineResponse;
import com.yeolsimee.roumo.app.routine.service.RoutineService;
import com.yeolsimee.roumo.app.user.entity.Role;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private String 루틴_이름;
    private List<String> 루틴_요일 = new ArrayList<>();
    private String 루틴_공개범위;
    private String 루틴_알람상태;
    private String 루틴_알람시간;
    private String 루틴_시간대;
    private String 카테고리_이름;
    @Autowired
    RoutineService routineService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserRepository userRepository;
    private User savedUser;

    @BeforeEach
    public void setUp() {
        User user = new User("test", "test", Role.ROLE_USER);
        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("루틴 생성하기")
    public void routineCreate() {
        //given
        루틴_이름 = "코딩하기";
        루틴_요일.add("MONDAY");
        루틴_요일.add("WEDNESDAY");
        루틴_요일.add("SUNDAY");
        루틴_공개범위 = "PUBLIC";
        루틴_알람상태 = "ON";
        루틴_알람시간 = "12";
        루틴_시간대 = "1";
        카테고리_이름 = "컴퓨터 하기";

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName(카테고리_이름);

        CategoryResponse categoryResponse = categoryService.insertCategory(categoryRequest, savedUser.getId());
        RoutineRequest routineRequest = new RoutineRequest(루틴_이름, categoryResponse.getCategoryId(), 루틴_요일, 루틴_공개범위, 루틴_알람상태, 루틴_알람시간, 루틴_시간대);

        //when
        RoutineResponse routineResponse = routineService.createRoutine(routineRequest, savedUser.getId());

        //then
        assertThat(routineResponse).isNotNull();
        assertThat(routineResponse.getRoutineName()).isEqualTo("코딩하기");
        assertThat(routineResponse.getWeekTypes()).contains("MONDAY", "WEDNESDAY", "SUNDAY");
        assertThat(routineResponse.getRoutineTimeZone()).isEqualTo("1");
    }
}
