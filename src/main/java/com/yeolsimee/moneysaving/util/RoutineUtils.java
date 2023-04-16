package com.yeolsimee.moneysaving.util;

public class RoutineUtils {
    public static String convertRoutineAchievementRateToRoutineAchievement(double routineAchievementRate) {
        String convertRoutineAchievement = "";
        if (routineAchievementRate * 100 >= 90) {
            convertRoutineAchievement = "GOLD";
        } else if (routineAchievementRate * 100 >= 30) {
            convertRoutineAchievement = "SILVER";
        } else {
            convertRoutineAchievement = "BRONZE";
        }
        return convertRoutineAchievement;
    }
}
