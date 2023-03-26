package com.yeolsimee.moneysaving.app.routine.dto;

import com.yeolsimee.moneysaving.app.routine.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RoutineDayResponse {
    private String routineDay;
    private List<CategoryData> categoryDatas;

    public static RoutineDayResponse of(List<Category> categories, String routineDay) {
        List<CategoryData> categoryDataList = new ArrayList<>();
        for (Category category : categories) {
            categoryDataList.add(CategoryData.of(category, routineDay));
        }
        return RoutineDayResponse.builder()
                .routineDay(routineDay)
                .categoryDatas(categoryDataList)
                .build();
    }
}
@Data
@Builder
class CategoryData {
    private String categoryId;
    private String categoryName;
    private String remainingRoutineNum;
    private List<RoutineData> routineDatas;

    public static CategoryData of(Category category, String routineDay) {
        List<RoutineData> routineDataList = new ArrayList<>();
        List<Routine> routines = category.getRoutines();
        for (Routine routine : routines) {
            routineDataList.add(RoutineData.of(routine, routineDay));
        }
        return CategoryData.builder()
                .categoryId(String.valueOf(category.getId()))
                .categoryName(category.getCategoryName())
                .remainingRoutineNum(String.valueOf(category.remainingRoutineNum(routineDay)))
                .routineDatas(routineDataList)
                .build();
    }
}
@Data
@Builder
class RoutineData{
    private String routineId;
    private String routineName;
    private String routineCheckYN;
    private String routineTimeZone;
    private String alarmTime;
    public static RoutineData of(Routine routine, String routineDay) {
        return RoutineData.builder()
                .routineId(String.valueOf(routine.getId()))
                .routineName(routine.getRoutineName())
                .routineCheckYN(String.valueOf(routine.getRoutineDay(routineDay).getRoutineCheckYn()))
                .routineTimeZone(String.valueOf(routine.getRoutineTimeZone()))
                .alarmTime(routine.getAlarmTime())
                .build();
    }
}