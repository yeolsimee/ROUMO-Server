package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.app.category.entity.Category;
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
}
