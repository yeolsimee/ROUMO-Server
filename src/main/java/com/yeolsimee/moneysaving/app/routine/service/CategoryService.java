package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.routine.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Transactional
    public Category createCategory(String categoryName) {
        return categoryRepository.save(Category.of(categoryName));
    }
}
