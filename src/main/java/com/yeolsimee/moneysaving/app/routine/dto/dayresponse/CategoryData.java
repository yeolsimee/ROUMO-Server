package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class CategoryData {
    private Long categoryId;
    private String categoryName;

    private Double routineCheckedRate;
    private List<RoutineData> routineDatas;

    @QueryProjection
    public CategoryData(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
