package com.yeolsimee.moneysaving.app.routine.service;

import com.yeolsimee.moneysaving.app.routine.dto.RoutineDaysRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineDaysResponse;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.repository.RoutineRepository;
import com.yeolsimee.moneysaving.app.user.entity.Role;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public RoutineResponse createRoutine(RoutineRequest routineRequest, Long userId) {
        //임시 유저
        User user = new User("name", "username", "email", "password", Role.ROLE_USER, "phoneNumber", "birthday", "address");

        Routine routine = routineRepository.save(RoutineRequest.toEntity(routineRequest, user));
        routine.addRoutineDays();
        return RoutineResponse.from(routine);
    }

    public RoutineDaysResponse findRoutineDays(Long userId, RoutineDaysRequest routineDaysRequest) {
        List<Routine> findedRoutines = routineRepository.findByUserId(userId);
        return RoutineDaysResponse.from(findedRoutines, routineDaysRequest);
    }
}
