package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public void createRoutine(RoutineRequest routineRequest, Long userId) {
        routineRepository.save(RoutineRequest.toEntity(routineRequest, userId));
    }
}
