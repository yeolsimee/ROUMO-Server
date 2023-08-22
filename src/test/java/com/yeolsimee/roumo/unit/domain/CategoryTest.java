package com.yeolsimee.roumo.unit.domain;

import com.yeolsimee.roumo.app.category.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("카테고리 단위 테스트")
public class CategoryTest {
    private String 카테고리_이름;

    @Test
    @DisplayName("루틴 생성하기")
    void createCategory() {
        카테고리_이름 = "컴퓨터 하기";
        Category category = Category.of(카테고리_이름);
        assertThat(category.getCategoryName()).isEqualTo(카테고리_이름);
    }

    @DisplayName("카테고리 순서 같은치 체크하기")
    @Test
    void equalCategoryOrder() {
    	// given
        카테고리_이름 = "컴퓨터 하기";
        Category category = Category.builder()
                .categoryName(카테고리_이름)
                .categoryOrder(1L)
                .build();
        // when
        boolean result = category.equalCategoryOrder(1L);

        // then
    	assertThat(result).isTrue();
    }
}
