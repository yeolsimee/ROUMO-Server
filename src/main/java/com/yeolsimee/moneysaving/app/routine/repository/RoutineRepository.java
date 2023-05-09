package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.entity.Routine;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long>, RoutineRepositoryCustom {
    Optional<Routine> findByIdAndUserName(Long routineId, String userName);

    List<Routine> findByUserId(Long userId);

    @Query("select count (r) from Routine r left JOIN r.weekTypes w where r.user.username = :userName and ((r.routineStartDate <= :routineDay and r.routineEndDate >= :routineDay and w = :weekType) or (r.routineStartDate = :routineDay and r.routineEndDate = :routineDay))")
    Integer findDayRoutineNum(String userName, String routineDay, WeekType weekType);

    @Query("select r from Routine r where r.category.id = :categoryId")
    List<Routine> findByRoutineByCategoryId(Long categoryId);

}
