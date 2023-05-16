package com.yeolsimee.moneysaving.app.category.dto;

import com.yeolsimee.moneysaving.app.category.entity.*;
import lombok.*;

@Builder
@Data
public class CategoryResponse {

    private Long categoryId;

    private String categoryName;

    public static CategoryResponse of(Category category) {

        return CategoryResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
