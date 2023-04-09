package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDays;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.yeolsimee.moneysaving.app.routineday.entity.RoutineCheckYN.N;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routineName;
    private String routineDeleteYN;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<WeekType> weekTypes;
    @Enumerated(EnumType.STRING)
    private RoutineType routineType;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;
    private String alarmTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private User user;
    @Embedded
    private RoutineDays routineDays = new RoutineDays();
    @Enumerated(EnumType.STRING)
    private RoutineTimeZone routineTimeZone;

    @Builder
    public Routine(User user, String routineName, Category category, List<WeekType> weekTypes, RoutineType routineType, AlarmStatus alarmStatus, String alarmTime, RoutineTimeZone routineTimeZone, String routineDeleteYN) {
        this.user = user;
        this.routineName = routineName;
        this.category = category;
        this.weekTypes = weekTypes;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
        this.routineTimeZone = routineTimeZone;
        this.routineDeleteYN = routineDeleteYN;
    }

    public List<RoutineDay> getRoutineDays() {
        return routineDays.getRoutineDays();
    }

    public RoutineDay getRoutineDay(String pickday) {
        return RoutineDays.of(routineDays.getRoutineDays()).routineDayByPickday(pickday).get(0);
    }

    public void addRoutineDays() {
        createOneYearRoutineDays();
    }

    public void updateRoutine(RoutineRequest routineRequest, Category category) {
        List<WeekType> weekTypeList = routineRequest.getWeekTypes().stream()
                .map(s -> WeekType.valueOf(s))
                .collect(Collectors.toList());

        this.routineName = routineRequest.getRoutineName();
        this.category = category;
        this.weekTypes = weekTypeList;
        this.routineType = RoutineType.valueOf(routineRequest.getRoutineType());
        this.alarmStatus = AlarmStatus.valueOf(routineRequest.getAlarmStatus());
        this.alarmTime = routineRequest.getAlarmTime();
        this.routineTimeZone = RoutineTimeZone.idOfRoutineTimeZone(routineRequest.getRoutineTimeZone());
    }

    public void updateRoutineDay() {
        deleteRoutineDayAfterToday();
        createOneYearRoutineDays();
    }

    private void createOneYearRoutineDays() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        if (weekTypes.isEmpty()) {
            routineDays.add(new RoutineDay(user, this, today.format(formatter), N));
            return;
        }
        for (int i = 0; i < 365; i++) {
            LocalDate date = today.plusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.MONDAY && weekTypes.contains(WeekType.MONDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.TUESDAY && weekTypes.contains(WeekType.TUESDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.WEDNESDAY && weekTypes.contains(WeekType.WEDNESDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.THURSDAY && weekTypes.contains(WeekType.THURSDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.FRIDAY && weekTypes.contains(WeekType.FRIDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.SATURDAY && weekTypes.contains(WeekType.SATURDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
            if (dayOfWeek == DayOfWeek.SUNDAY && weekTypes.contains(WeekType.SUNDAY)) {
                routineDays.add(new RoutineDay(user, this, date.format(formatter), N));
                continue;
            }
        }
    }

    private void deleteRoutineDayAfterToday() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        routineDays.deleteRoutineDayAfterToday(today.format(formatter));
    }

    public void deleteRoutineDay() {
        deleteRoutineDayAfterToday();
    }

    public void changeRoutineDeleteYN(String routineDeleteYN) {
        this.routineDeleteYN = routineDeleteYN;
    }
}


