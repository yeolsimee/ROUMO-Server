package com.yeolsimee.moneysaving.app.routineday.dto;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DayResponse {
    private String routineDay;
    private List<CategoryData> categoryDatas;

    public static DayResponse of(List<Category> categories, String routineDay) {
        List<CategoryData> categoryDataList = new ArrayList<>();
        for (Category category : categories) {
            categoryDataList.add(CategoryData.of(category, routineDay));
        }
        return DayResponse.builder()
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
    private String routineDayId;
    private String routineName;
    private String routineCheckYN;
    private String routineTimeZone;
    private String alarmTime;
    public static RoutineData of(Routine routine, String routineDay) {
        return RoutineData.builder()
                .routineDayId(String.valueOf(routine.getRoutineDay(routineDay).getId()))
                .routineName(routine.getRoutineName())
                .routineCheckYN(String.valueOf(routine.getRoutineDay(routineDay).getRoutineCheckYn()))
                .routineTimeZone(String.valueOf(routine.getRoutineTimeZone()))
                .alarmTime(routine.getAlarmTime())
                .build();
    }
}