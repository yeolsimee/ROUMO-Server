package com.yeolsimee.moneysaving.app.routineday.service;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.common.exception.EntityNotFoundException;
import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;
import com.yeolsimee.moneysaving.app.routineday.dto.DayResponse;
import com.yeolsimee.moneysaving.app.routineday.dto.RoutineDayResponse;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineCheckYN;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import com.yeolsimee.moneysaving.app.routineday.repository.RoutineDayRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineDayService {
    private final RoutineDayRespository routineDayRespository;

    public DayResponse findRoutineDay(Long userId, String pickday) {
        List<RoutineDay> myRoutineDaysByPickday = findRoutineDaysByRoutineDay(pickday).stream()
                .filter(routineDay -> routineDay.getUser().getId() == userId)
                .collect(Collectors.toList());

        List<Category> categories = myRoutineDaysByPickday.stream()
                .map(routineDay -> routineDay.getRoutine().getCategory())
                .distinct()
                .collect(Collectors.toList());

        return DayResponse.of(categories, pickday);
    }

    public List<RoutineDay> findRoutineDaysByRoutineDay(String routineDay) {
        return routineDayRespository.findAllByRoutineDay(routineDay);
    }

    public RoutineDayResponse updateRoutineCheck(String routineDayId, String routineCheckYN) {
        RoutineDay routineDay = routineDayRespository.findById(Long.valueOf(routineDayId)).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_VALID_ROUTINE_DAY));
        routineDay.changeRoutineCheckYn(RoutineCheckYN.valueOf(routineCheckYN));
        return RoutineDayResponse.from(routineDay);
    }
}