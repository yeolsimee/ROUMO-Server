package com.yeolsimee.roumo.app.category.dto;

import com.yeolsimee.roumo.app.category.entity.*;
import com.yeolsimee.roumo.app.user.entity.*;
import lombok.*;

@Data
public class CategoryRequest {

    private String userId;

    private String categoryId;

    private String categoryName;

    public static Category toEntity(CategoryRequest categoryRequest, User user) {

        return Category.builder()
                .user(user)
                .categoryName(categoryRequest.getCategoryName())
                .build();

    }
}
