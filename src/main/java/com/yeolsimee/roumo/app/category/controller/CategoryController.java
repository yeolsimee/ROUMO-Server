package com.yeolsimee.roumo.app.category.controller;

import com.yeolsimee.roumo.app.category.dto.*;
import com.yeolsimee.roumo.app.category.service.*;
import com.yeolsimee.roumo.app.common.response.service.*;
import com.yeolsimee.roumo.app.user.entity.User;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final ResponseService responseService;
    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getCategories(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getListResult(categoryService.getCategories(user.getId())));
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> insertCategory(@RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.insertCategory(categoryRequest, user.getId())));
    }

    @PostMapping("/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.updateCategory(categoryRequest, user.getId())));
    }

    @PostMapping("/category/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User user){
        categoryService.deleteCategory(Long.valueOf(categoryRequest.getCategoryId()), user.getId());
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/category/order/update")
    public ResponseEntity<?> updateCategoryOrder(@RequestBody UpdateCategoryOrderRequest updateCategoryOrderRequest, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.updateCategoryOrder(user.getId(), updateCategoryOrderRequest.getCategoryId(), updateCategoryOrderRequest.getCategoryOrder())));
    }
}
