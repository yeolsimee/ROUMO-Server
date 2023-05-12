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
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(responseService.getListResult(categoryService.list()));
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> insert(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(responseService.getSingleResult(categoryService.insert(categoryRequest)));
    }

    @PostMapping("/category/update")
    public ResponseEntity<?> update(@RequestBody CategoryRequest categoryRequest){
        categoryService.update(categoryRequest);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/category/delete")
    public ResponseEntity<?> delete(@RequestBody CategoryRequest categoryRequest, @AuthenticationPrincipal User user){
        categoryService.deleteCategory(categoryRequest.getCategoryId(), user.getId());
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

}
