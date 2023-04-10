package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.category.service.CategoryService;
import com.yeolsimee.moneysaving.app.common.exception.EntityNotFoundException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;
import com.yeolsimee.moneysaving.app.routine.dto.*;
import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final CategoryService categoryService;
    private final UserService userService;


    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        User user = userService.getUserByUserId(userId);
        Category category = findOrCreateCategory(routineRequest, userId);
        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user, "N"));
        routine.addRoutineDays();
        return RoutineResponse.from(routine);
    }

    @Transactional
    public RoutineResponse updateRoutine(RoutineRequest routineRequest, Long userId, Long routineId) {
        Category category = findOrCreateCategory(routineRequest, userId);
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        routine.updateRoutine(routineRequest, category);
        routine.updateRoutineDay();
        return RoutineResponse.from(routine);
    }

    @Transactional
    public void deleteRoutine(long id, Long routineId) {
        Routine routine = routineRepository.findById(routineId).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE));
        routine.deleteRoutineDay();
        routine.changeRoutineDeleteYN("Y");
    }

    private Category findOrCreateCategory(RoutineRequest routineRequest, Long userId) {
        List<Routine> routines = routineRepository.findByUserId(userId);

        Category category = routines.stream()
                .map(routine -> routine.getCategory())
                .filter(myCategory -> myCategory.getCategoryName().contains(routineRequest.getCategoryName()))
                .findFirst()
                .orElse(categoryService.createCategory(routineRequest.getCategoryName()));
        return category;
    }
}
