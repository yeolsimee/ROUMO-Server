package com.yeolsimee.moneysaving.app.category.service;

import com.yeolsimee.moneysaving.app.category.dto.*;
import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.category.repository.CategoryRepository;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.common.exception.EntityNotFoundException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final RoutineService routineService;

    @Transactional
    public Category createCategory(String categoryName) {
        return categoryRepository.save(Category.of(categoryName));
    }

    public List<CategoryResponse> list(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Category> categoryList = categoryRepository.findByUser_Id(user.getId());
        return categoryList.stream().map(CategoryResponse::of).toList();
    }

    @Transactional
    public CategoryResponse insert(CategoryRequest categoryRequest){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        category.changeCategoryDeleteYN("N");
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    @Transactional
    public void update(CategoryRequest categoryRequest) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(CategoryRequest categoryRequest){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        categoryRepository.delete(category);
    }

    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(()-> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
    }

    @Transactional
    public void deleteCategory(Long categoryId, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
        category.changeCategoryDeleteYN("Y");
        List<Routine> routineByCategoryId = routineService.findRoutineByCategoryId(category.getId());
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        for (Routine routine : routineByCategoryId) {
            routineService.deleteRoutineOrChangeEndDate(routine, today);
        }
    }
}
