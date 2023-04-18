package com.yeolsimee.moneysaving.app.category.entity;

import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(String categoryName) {
        return new Category(categoryName);
    }
}
