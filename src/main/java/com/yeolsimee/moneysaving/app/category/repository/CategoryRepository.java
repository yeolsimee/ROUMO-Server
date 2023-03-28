package com.yeolsimee.moneysaving.app.category.repository;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
