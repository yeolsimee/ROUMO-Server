package com.yeolsimee.moneysaving.app.routine.dto.dayresponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DayResponse {
    private String routineDay;
    private List<CategoryData> categoryDatas;

    public DayResponse(String routineDay, List<CategoryData> categoryDatas) {
        this.routineDay = routineDay;
        this.categoryDatas = categoryDatas;
    }

    public static DayResponse of(String routineDay, List<CategoryData> categoryDatas) {
        return new DayResponse(routineDay, categoryDatas);
    }
}

