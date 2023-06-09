package com.yeolsimee.roumo.app.category.service;

import com.yeolsimee.roumo.app.category.dto.CategoryRequest;
import com.yeolsimee.roumo.app.category.dto.CategoryResponse;
import com.yeolsimee.roumo.app.category.entity.Category;
import com.yeolsimee.roumo.app.category.repository.CategoryRepository;
import com.yeolsimee.roumo.app.common.exception.EntityNotFoundException;
import com.yeolsimee.roumo.app.common.response.ResponseMessage;
import com.yeolsimee.roumo.app.routine.entity.Routine;
import com.yeolsimee.roumo.app.routine.service.RoutineService;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final RoutineService routineService;
    private final UserService userService;

    public List<CategoryResponse> getCategories(Long userId){
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        return categoryList.stream().map(CategoryResponse::of).toList();
    }

    @Transactional
    public CategoryResponse insertCategory(CategoryRequest categoryRequest, Long userId) {
        User user = userService.getUserByUserId(userId);
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        category.changeCategoryDeleteYN("N");
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    @Transactional
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(Long.parseLong(categoryRequest.getCategoryId()), userId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
        category.changeCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);
        return CategoryResponse.of(category);
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
