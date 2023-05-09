package com.yeolsimee.moneysaving.app.category.controller;

import com.yeolsimee.moneysaving.app.category.dto.*;
import com.yeolsimee.moneysaving.app.category.service.*;
import com.yeolsimee.moneysaving.app.common.response.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final ResponseService responseService;
    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> list(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getListResult(categoryService.list(user.getUsername())));
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> insert(@AuthenticationPrincipal User user, @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.insert(categoryRequest, user.getUsername())));
    }

    @PostMapping("/category/update")
    public ResponseEntity<?> update(@AuthenticationPrincipal User user, @RequestBody CategoryRequest categoryRequest){
        categoryService.update(categoryRequest, user.getUsername());
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/category/delete/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId, @AuthenticationPrincipal User user){
        categoryService.deleteCategory(categoryId, user.getUsername());
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

}
