package com.yeolsimee.moneysaving.app.category.dto;

import com.yeolsimee.moneysaving.app.category.entity.*;
import lombok.*;

@Builder
@Data
public class CategoryResponse {

    private long id;

    private String name;

    public static CategoryResponse of(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getCategoryName())
                .build();
    }
}
