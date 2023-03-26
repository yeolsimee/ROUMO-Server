package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CategoryName;
    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    public Category(String categoryName) {
        CategoryName = categoryName;
    }

    public static Category of(String categoryName) {
        return new Category(categoryName);
    }

    public long remainingRoutineNum(String pickday) {
        return routines.stream().map(routine -> routine.getRoutineDays())
                .filter(routineDays -> RoutineDays.of(routineDays).isRoutineDayByPickday(pickday)).count();
    }
}
