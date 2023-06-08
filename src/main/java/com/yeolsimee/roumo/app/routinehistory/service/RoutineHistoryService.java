package com.yeolsimee.roumo.app.routinehistory.service;

import com.yeolsimee.roumo.app.routine.dto.dayresponse.DayResponse;
import com.yeolsimee.roumo.app.routine.entity.Routine;
import com.yeolsimee.roumo.app.routine.service.RoutineService;
import com.yeolsimee.roumo.app.routinehistory.dto.RoutineCheckRequest;
import com.yeolsimee.roumo.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.roumo.app.routinehistory.entity.RoutineHistory;
import com.yeolsimee.roumo.app.routinehistory.repository.RoutineHistoryRepository;
import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineHistoryService {
    private final RoutineHistoryRepository routineHistoryRepository;
    private final UserService userService;
    private final RoutineService routineService;

    @Transactional
    public DayResponse changeOrCreateRoutineCheck(Long userId, RoutineCheckRequest routineCheckRequest) {
        RoutineHistory routineHistory = null;
        User user = userService.getUserByUserId(userId);
        Routine routine = routineService.findRoutineByRoutineIdAndUserId(routineCheckRequest.getRoutineId(), userId);
        Optional<RoutineHistory> findedRoutineHistory = routineHistoryRepository.findRoutineHistory(userId, routineCheckRequest.getRoutineId(), routineCheckRequest.getRoutineDay());

        if (findedRoutineHistory.isPresent()) {
            findedRoutineHistory.get().changeRoutineCheckYn(RoutineCheckYN.valueOf(routineCheckRequest.getRoutineCheckYN()));
            routineHistory = findedRoutineHistory.get();
        } else {
            routineHistory = RoutineCheckRequest.toEntity(routineCheckRequest, user, routine);
            routineHistoryRepository.save(routineHistory);
        }
        DayResponse routineDay = routineService.findRoutineDay(userId, routineHistory.getRoutineDay(), "Y");
        return routineDay;
    }
}
