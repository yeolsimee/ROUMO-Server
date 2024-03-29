package com.yeolsimee.roumo.app.category.repository;

import com.yeolsimee.roumo.app.category.entity.Category;
import com.yeolsimee.roumo.app.user.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.categoryDeleteYN <> 'Y' and c.user.id = :userId")
    List<Category> findByUserId(@NonNull long userId);

    @Query("select c from Category c where c.id = :categoryId and c.user.id = :userId")
    Optional<Category> findByIdAndUserId(long categoryId, long userId);

    void deleteByUser(User user);
}
