package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.entity.RoutineDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineDayRespository extends JpaRepository<RoutineDay, Long> {
    List<RoutineDay> findAllByRoutineDay(String routineDay);
}
