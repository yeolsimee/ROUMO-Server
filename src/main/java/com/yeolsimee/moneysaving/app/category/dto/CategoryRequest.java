package com.yeolsimee.moneysaving.app.category.dto;

import com.yeolsimee.moneysaving.app.category.entity.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

@Data
public class CategoryRequest {

    private long id;

    private long userId;

    private String categoryName;

    public static Category toEntity(CategoryRequest categoryRequest, User user) {

        return Category.builder()
                .id(categoryRequest.getId())
                .user(user)
                .categoryName(categoryRequest.getCategoryName())
                .build();

    }
}
