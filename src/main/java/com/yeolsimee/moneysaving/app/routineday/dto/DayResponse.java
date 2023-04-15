package com.yeolsimee.moneysaving.app.routineday.dto;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.util.TimeUtils;
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
    private String alarmTimeHour;
    private String alarmTimeMinute;
    public static RoutineData of(Routine routine, String routineDay) {
        return RoutineData.builder()
                .routineId(String.valueOf(routine.getId()))
                .routineName(routine.getRoutineName())
                //todo: 추후에 수정 필요
//                .routineCheckYN(String.valueOf(routine.getRoutineDay(routineDay).getRoutineCheckYn()))
                .routineTimeZone(String.valueOf(routine.getRoutineTimeZone()))
                .alarmTimeHour(TimeUtils.convertAlarmTimeToHourMinute(routine.getAlarmTime()).get("timeHour"))
                .alarmTimeMinute(TimeUtils.convertAlarmTimeToHourMinute(routine.getAlarmTime()).get("timeMinute"))
                .build();
    }
}