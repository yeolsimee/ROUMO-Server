package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryData {
    private String categoryId;
    private String categoryName;
    private List<RoutineData> routineDatas;

    @QueryProjection
    public CategoryData(String categoryId, String categoryName, List<RoutineData> routineDatas) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.routineDatas = routineDatas;
    }
}
