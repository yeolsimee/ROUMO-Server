package com.yeolsimee.roumo.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class CategoryData {
    private String categoryId;
    private String categoryName;
    private Long categoryOrder;

    private Double routineCheckedRate;
    private List<RoutineData> routineDatas;

    @QueryProjection
    public CategoryData(String categoryId, String categoryName, Long categoryOrder) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
    }

}
