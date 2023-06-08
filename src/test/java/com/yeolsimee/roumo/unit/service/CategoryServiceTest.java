package com.yeolsimee.roumo.unit.service;

import com.yeolsimee.roumo.app.category.dto.CategoryRequest;
import com.yeolsimee.roumo.app.category.dto.CategoryResponse;
import com.yeolsimee.roumo.app.category.service.CategoryService;
import com.yeolsimee.roumo.app.user.entity.Role;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("카테고리 서비스 테스트")
@SpringBootTest
@Transactional
public class CategoryServiceTest {
    private String 카테고리_이름;
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
    @DisplayName("카테고리 생성하기")
    public void categoryCreate() {
        카테고리_이름 = "컴퓨터하기";
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName(카테고리_이름);
        CategoryResponse categoryResponse = categoryService.insertCategory(categoryRequest, savedUser.getId());
        assertThat(categoryResponse.getCategoryId()).isEqualTo("1");
        assertThat(categoryResponse.getCategoryName()).isEqualTo(카테고리_이름);
    }
}
