package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, userId));
        routine.addRoutineDays();
        return RoutineResponse.from(routine);
    }
}
