package com.yeolsimee.moneysaving.app.category.controller;

import com.yeolsimee.moneysaving.app.category.dto.*;
import com.yeolsimee.moneysaving.app.category.service.*;
import com.yeolsimee.moneysaving.app.common.response.service.*;
import com.yeolsimee.moneysaving.app.user.entity.User;
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
    public ResponseEntity<?> getCategories(){
        return ResponseEntity.ok(responseService.getListResult(categoryService.getCategories()));
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> insertCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.insertCategory(categoryRequest)));
    }

    @PostMapping("/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.updateCategory(categoryRequest);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/category/delete")
    public ResponseEntity<?> deleteCategory(@RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User user){
        categoryService.deleteCategory(categoryRequest.getCategoryId(), user.getId());
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

}
