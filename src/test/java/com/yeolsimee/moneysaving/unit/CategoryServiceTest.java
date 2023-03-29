package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.category.service.CategoryService;
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

    @Test
    public void categoryCreate() {
        카테고리_이름 = "컴퓨터하기";
        Category category = categoryService.createCategory(카테고리_이름);
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getCategoryName()).isEqualTo(카테고리_이름);
    }
}
