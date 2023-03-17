package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routineDay;
    @Enumerated(EnumType.STRING)
    private RoutineCheckYN routineCheckYn;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    public RoutineDay(Routine routine, String routineDay, RoutineCheckYN routineCheckYn) {
        this.routine = routine;
        this.routineDay = routineDay;
        this.routineCheckYn = routineCheckYn;
    }
}
