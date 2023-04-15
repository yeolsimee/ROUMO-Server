package com.yeolsimee.moneysaving.app.category.repository;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_Id(@NonNull long userId);

}
