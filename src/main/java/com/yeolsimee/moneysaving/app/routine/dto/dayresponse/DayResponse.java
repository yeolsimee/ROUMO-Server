package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DayResponse {
    private String routineDay;
    private List<CategoryData> categoryDatas;

    @QueryProjection
    public DayResponse(String routineDay, List<CategoryData> categoryDatas) {
        this.routineDay = routineDay;
        this.categoryDatas = categoryDatas;
    }
}

