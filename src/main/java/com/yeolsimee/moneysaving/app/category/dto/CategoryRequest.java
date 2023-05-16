package com.yeolsimee.moneysaving.app.category.dto;

import com.yeolsimee.moneysaving.app.category.entity.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

@Data
public class CategoryRequest {

    private Long userId;

    private Long categoryId;

    private String categoryName;

    public static Category toEntity(CategoryRequest categoryRequest, User user) {

        return Category.builder()
                .user(user)
                .categoryName(categoryRequest.getCategoryName())
                .build();

    }
}
