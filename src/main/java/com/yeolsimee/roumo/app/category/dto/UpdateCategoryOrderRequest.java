package com.yeolsimee.roumo.app.category.dto;

import lombok.Data;

@Data
public class UpdateCategoryOrderRequest {
	private Long firstCategoryId;
	private Long secondCategoryId;
}
