package com.yeolsimee.moneysaving.app.category.service;

import com.yeolsimee.moneysaving.app.category.dto.CategoryRequest;
import com.yeolsimee.moneysaving.app.category.dto.CategoryResponse;
import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.category.repository.CategoryRepository;
import com.yeolsimee.moneysaving.app.common.exception.EntityNotFoundException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.service.UserService;
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

    @Transactional
    public Category createCategory(String categoryName) {
        return categoryRepository.save(Category.of(categoryName));
    }

    public List<CategoryResponse> list(String userName){
        User user = userService.getUserByUid(userName);
        List<Category> categoryList = categoryRepository.findByUser_Id(user.getId());
        return categoryList.stream().map(CategoryResponse::of).toList();
    }

    @Transactional
    public CategoryResponse insert(CategoryRequest categoryRequest, String userName){
        User user = userService.getUserByUid(userName);
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        category.changeCategoryDeleteYN("N");
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    @Transactional
    public void update(CategoryRequest categoryRequest, String userName) {
        User user = userService.getUserByUid(userName);
        Category category = CategoryRequest.toEntity(categoryRequest, user);
        categoryRepository.save(category);
    }

    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(()-> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
    }

    @Transactional
    public void deleteCategory(Long categoryId, String userName) {
        Category category = categoryRepository.findByIdAndUserName(categoryId, userName).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_CATEGORY));
        category.changeCategoryDeleteYN("Y");
        List<Routine> routineByCategoryId = routineService.findRoutineByCategoryId(category.getId());
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        for (Routine routine : routineByCategoryId) {
            routineService.deleteRoutineOrChangeEndDate(routine, today);
        }
    }
}
