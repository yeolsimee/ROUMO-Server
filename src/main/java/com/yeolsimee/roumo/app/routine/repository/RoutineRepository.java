package com.yeolsimee.roumo.app.routine.repository;

import com.yeolsimee.roumo.app.routine.entity.Routine;
import com.yeolsimee.roumo.app.routine.entity.WeekType;
import com.yeolsimee.roumo.app.user.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {

    Optional<Routine> findByIdAndUserId(Long routineId, Long userId);

    @Query("select r from Routine r where r.user.id = :userId and r.routineStartDate <= :routineDay and r.routineEndDate >= :routineDay and r.alarmStatus = 'ON'")
    List<Routine> findByUserId(Long userId, String routineDay);

    @Query("select count (r) from Routine r left JOIN r.weekTypes w where r.user.id = :userId and ((r.routineStartDate <= :routineDay and r.routineEndDate >= :routineDay and w = :weekType) or (r.routineStartDate = :routineDay and r.routineEndDate = :routineDay))")
    Integer findDayRoutineNum(Long userId, String routineDay, WeekType weekType);

    @Query("select r from Routine r where r.category.id = :categoryId")
    List<Routine> findByRoutineByCategoryId(Long categoryId);

    List<Routine> findByUserId(Long userId);

    void deleteByUser(User user);

}
