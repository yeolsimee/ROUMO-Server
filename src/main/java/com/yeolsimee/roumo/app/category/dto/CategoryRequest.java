package com.yeolsimee.roumo.app.category.dto;

import com.yeolsimee.roumo.app.category.entity.*;
import com.yeolsimee.roumo.app.user.entity.*;
import lombok.*;

@Data
public class CategoryRequest {

    private String userId;

    private String categoryId;

    private String categoryName;

    private Long categoryOrder;

    public static Category toEntity(CategoryRequest categoryRequest, User user) {

        return Category.builder()
                .user(user)
                .categoryName(categoryRequest.getCategoryName())
                .categoryOrder(categoryRequest.getCategoryOrder())
                .build();

    }
}
