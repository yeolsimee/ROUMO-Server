package com.yeolsimee.moneysaving.app.routinehistory.service;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import com.yeolsimee.moneysaving.app.routinehistory.dto.RoutineCheckRequest;
import com.yeolsimee.moneysaving.app.routinehistory.dto.RoutineHistoryResponse;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineHistory;
import com.yeolsimee.moneysaving.app.routinehistory.repository.RoutineHistoryRepository;
import com.yeolsimee.moneysaving.app.user.entity.User;
import com.yeolsimee.moneysaving.app.user.service.UserService;
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
    public RoutineHistoryResponse changeOrCreateRoutineCheck(Long userId, RoutineCheckRequest routineCheckRequest) {
        RoutineHistory routineHistory = null;
        User user = userService.getUserByUserId(userId);
        Routine routine = routineService.findRoutineByRoutineId(routineCheckRequest.getRoutineId());
        Optional<RoutineHistory> findedRoutineHistory = routineHistoryRepository.findRoutineHistory(userId, routineCheckRequest.getRoutineId(), routineCheckRequest.getRoutineDay());

        if (findedRoutineHistory.isPresent()) {
            findedRoutineHistory.get().changeRoutineCheckYn(RoutineCheckYN.valueOf(routineCheckRequest.getRoutineCheckYN()));
            routineHistory = findedRoutineHistory.get();
        } else {
            routineHistory = RoutineCheckRequest.toEntity(routineCheckRequest, user, routine);
            routineHistoryRepository.save(routineHistory);
        }

        return RoutineHistoryResponse.from(routineHistory);
    }
}
