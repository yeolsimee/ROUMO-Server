package com.yeolsimee.roumo.app.category.entity;

import com.yeolsimee.roumo.app.common.entity.BaseEntity;
import com.yeolsimee.roumo.app.user.entity.User;
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

    private String categoryDeleteYN;

    private Long categoryOrder;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @PostPersist
    public void postPersist() {
        if (categoryOrder == null) {
            categoryOrder = id; // customField 값을 id 값으로 설정
        }
    }

    public static Category of(String categoryName) {
        return new Category(categoryName);
    }

    public void changeCategoryDeleteYN(String categoryDeleteYN) {
        this.categoryDeleteYN = categoryDeleteYN;
    }

    public void changeCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void changeCategoryOrder(Long categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    public boolean equalCategoryOrder(long categoryOrder) {
        return this.categoryOrder.equals(categoryOrder);
    }
}
