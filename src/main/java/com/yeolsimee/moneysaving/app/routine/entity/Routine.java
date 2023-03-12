package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String routineName;
    private String routineCategory;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<RoutineDayWeek> routineDayWeeks;
    @Enumerated(EnumType.STRING)
    private RoutineType routineType;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;
    private String alarmTime;
    @ElementCollection
    private List<String> routineAchievedDays;

    @Builder
    public Routine(Long userId, String routineName, String routineCategory, List<RoutineDayWeek> routineDayWeeks, RoutineType routineType, AlarmStatus alarmStatus, String alarmTime) {
        this.userId = userId;
        this.routineName = routineName;
        this.routineCategory = routineCategory;
        this.routineDayWeeks = routineDayWeeks;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
    }
}


