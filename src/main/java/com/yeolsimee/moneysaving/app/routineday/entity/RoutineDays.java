package com.yeolsimee.moneysaving.app.routineday.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RoutineDays {
    @OneToMany(mappedBy = "routine", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoutineDay> routineDays = new ArrayList<>();

    public void add(RoutineDay routineDay) {
        routineDays.add(routineDay);
    }

    public RoutineDays(List<RoutineDay> routineDays) {
        this.routineDays = routineDays;
    }

    public static RoutineDays of(List<RoutineDay> routineDays) {
        return new RoutineDays(routineDays);
    }

    public List<RoutineDay> routineDayByPickday(String pickday) {
        return routineDays.stream().filter(routineDay -> routineDay.getRoutineDay().equals(pickday)).collect(Collectors.toList());
    }

    public boolean isRoutineDayByPickday(String pickday) {
        return routineDays.stream().anyMatch(routineDay -> routineDay.getRoutineDay().contains(pickday));
    }
}
