package com.yeolsimee.moneysaving.app.routineday.repository;

import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoutineDayRespository extends JpaRepository<RoutineDay, Long> {
    List<RoutineDay> findAllByRoutineDay(String routineDay);

    @Query("select r from RoutineDay r where r.user.id = :userId and r.routineDay >= :startDate and r.routineDay <= :endDate")
    List<RoutineDay> findByUserIdWithDate(Long userId, String startDate, String endDate);
}
