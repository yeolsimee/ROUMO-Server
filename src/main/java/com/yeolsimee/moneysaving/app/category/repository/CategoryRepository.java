package com.yeolsimee.moneysaving.app.category.repository;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_Id(@NonNull long userId);
    @Query("select c from Category c where c.id = :categoryId and c.user.username = :userName")
    Optional<Category> findByIdAndUserName(long categoryId, String userName);

}
