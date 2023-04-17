package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {
    Optional<Routine> findByIdAndUserId(Long routineId, Long userId);

    List<Routine> findByUserId(Long userId);

    @Query("select count (r) from Routine r JOIN r.weekTypes w where r.user.id = :userId and r.routineStartDate <= :routineDay and r.routineEndDate >= :routineDay and w = :weekType")
    Integer findDayRoutineNum(Long userId, String routineDay, WeekType weekType);
}
