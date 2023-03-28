package com.yeolsimee.moneysaving.app.routineday.service;

import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import com.yeolsimee.moneysaving.app.routineday.repository.RoutineDayRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineDayService {
    private final RoutineDayRespository routineDayRespository;

    public List<RoutineDay> findRoutineDaysByRoutineDay(String routineDay) {
        return routineDayRespository.findAllByRoutineDay(routineDay);
    }
}
