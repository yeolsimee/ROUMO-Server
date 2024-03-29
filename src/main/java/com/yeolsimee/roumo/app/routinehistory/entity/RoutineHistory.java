package com.yeolsimee.roumo.app.routinehistory.entity;

import com.yeolsimee.roumo.app.common.entity.BaseEntity;
import com.yeolsimee.roumo.app.routine.entity.Routine;
import com.yeolsimee.roumo.app.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routineDay;
    @Enumerated(EnumType.STRING)
    private RoutineCheckYN routineCheckYn;
    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RoutineHistory(User user, Routine routine, String routineDay, RoutineCheckYN routineCheckYn) {
        this.user = user;
        this.routine = routine;
        this.routineDay = routineDay;
        this.routineCheckYn = routineCheckYn;
    }

    public void changeRoutineCheckYn(RoutineCheckYN routineCheckYN){
        this.routineCheckYn = routineCheckYN;
    }
}
