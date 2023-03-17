package com.yeolsimee.moneysaving.app.routine.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineDays {
    @OneToMany(mappedBy = "routine", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RoutineDay> routineDays = new ArrayList<>();

    public void add(RoutineDay routineDay) {
        routineDays.add(routineDay);
    }
}
