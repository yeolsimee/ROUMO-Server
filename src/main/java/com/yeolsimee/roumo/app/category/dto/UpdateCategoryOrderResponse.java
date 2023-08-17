package com.yeolsimee.roumo.app.category.dto;

import com.yeolsimee.roumo.app.category.entity.Category;
import lombok.Data;

@Data
public class UpdateCategoryOrderResponse {
	private CategoryResponse firstCategory;
	private CategoryResponse secondCategory;

	public UpdateCategoryOrderResponse(CategoryResponse firstCategory, CategoryResponse secondCategory) {
		this.firstCategory = firstCategory;
		this.secondCategory = secondCategory;
	}

	public static UpdateCategoryOrderResponse of(Category firstCategory, Category secondCategory) {
		CategoryResponse firstCategoryResponse = CategoryResponse.builder()
				.categoryId(String.valueOf(firstCategory.getId()))
				.categoryName(firstCategory.getCategoryName())
				.categoryOrder(firstCategory.getCategoryOrder())
				.build();

		CategoryResponse secondCategoryResponse = CategoryResponse.builder()
				.categoryId(String.valueOf(secondCategory.getId()))
				.categoryName(secondCategory.getCategoryName())
				.categoryOrder(secondCategory.getCategoryOrder())
				.build();
		return new UpdateCategoryOrderResponse(firstCategoryResponse, secondCategoryResponse);
	}
}
