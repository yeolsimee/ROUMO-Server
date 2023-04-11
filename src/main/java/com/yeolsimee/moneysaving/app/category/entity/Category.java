package com.yeolsimee.moneysaving.app.category.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDays;
import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(String categoryName) {
        return new Category(categoryName);
    }

    public long remainingRoutineNum(String pickday) {
        return routines.stream().map(routine -> routine.getRoutineDays())
                .filter(routineDays -> RoutineDays.of(routineDays).isRoutineDayByPickday(pickday)).count();
    }
}
