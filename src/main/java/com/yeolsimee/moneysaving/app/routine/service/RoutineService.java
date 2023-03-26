package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.routine.dto.*;
import com.yeolsimee.moneysaving.app.routine.entity.Category;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.RoutineDay;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final CategoryService categoryService;
    private final RoutineDayService routineDayService;
    private final CustomUserDetailService customUserDetailService;


    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        User user = customUserDetailService.getUserByUserId(userId);
        Category category = findOrCreateCategory(routineRequest, userId);
        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, category, user));
        routine.addRoutineDays();
        return RoutineResponse.from(routine);
    }

    public RoutineDaysResponse findRoutineDays(Long userId, RoutineDaysRequest routineDaysRequest) {
        List<Routine> findedRoutines = routineRepository.findByUserId(userId);
        return RoutineDaysResponse.from(findedRoutines, routineDaysRequest);
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

    public RoutineDayResponse findRoutineDay(Long userId, String pickday) {
        List<RoutineDay> myRoutineDaysByPickday = routineDayService.findRoutineDaysByRoutineDay(pickday).stream()
                .filter(routineDay -> routineDay.getUser().getId() == userId)
                .collect(Collectors.toList());

        List<Category> categories = myRoutineDaysByPickday.stream()
                .map(routineDay -> routineDay.getRoutine().getCategory())
                .distinct()
                .collect(Collectors.toList());

        return RoutineDayResponse.of(categories, pickday);
    }
}