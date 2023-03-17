package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.yeolsimee.moneysaving.app.routine.entity.RoutineCheckYN.N;


@Entity
@Getter
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
    private List<WeekType> weekTypes;
    @Enumerated(EnumType.STRING)
    private RoutineType routineType;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;
    private String alarmTime;
    @Embedded
    private RoutineDays routineDays = new RoutineDays();
    @Builder
    public Routine(Long userId, String routineName, String routineCategory, List<WeekType> weekTypes, RoutineType routineType, AlarmStatus alarmStatus, String alarmTime) {
        this.userId = userId;
        this.routineName = routineName;
        this.routineCategory = routineCategory;
        this.weekTypes = weekTypes;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
    }

    public List<RoutineDay> getRoutineDays() {
        return routineDays.getRoutineDays();
    }

    public void addRoutineDays() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        createOneYearRoutineDays(today, formatter);
    }

    private void createOneYearRoutineDays(LocalDate today, DateTimeFormatter formatter) {
        if (weekTypes.isEmpty()) {
            routineDays.add(new RoutineDay(this, today.format(formatter), N));
            return;
        }
        for (int i = 0; i < 365; i++) {
            LocalDate date = today.plusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.MONDAY && weekTypes.contains(WeekType.MONDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.TUESDAY && weekTypes.contains(WeekType.TUESDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.WEDNESDAY && weekTypes.contains(WeekType.WEDNESDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.THURSDAY && weekTypes.contains(WeekType.THURSDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.FRIDAY && weekTypes.contains(WeekType.FRIDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.SATURDAY && weekTypes.contains(WeekType.SATURDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
            if (dayOfWeek == DayOfWeek.SUNDAY && weekTypes.contains(WeekType.SUNDAY)) {
                routineDays.add(new RoutineDay(this, date.format(formatter), N));
            }
        }
    }
}


