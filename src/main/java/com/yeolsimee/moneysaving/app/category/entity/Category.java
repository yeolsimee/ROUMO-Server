package com.yeolsimee.moneysaving.app.category.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.user.entity.*;
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
}
