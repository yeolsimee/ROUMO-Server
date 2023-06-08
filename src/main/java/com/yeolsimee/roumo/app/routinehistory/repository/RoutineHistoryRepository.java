package com.yeolsimee.roumo.app.routinehistory.repository;

import com.yeolsimee.roumo.app.routinehistory.entity.RoutineCheckYN;
import com.yeolsimee.roumo.app.routinehistory.entity.RoutineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoutineHistoryRepository extends JpaRepository<RoutineHistory, Long> {

    @Query("select rh from RoutineHistory rh inner join rh.routine r inner join rh.user u where rh.routineDay = :routineDay and r.id = :routineId and u.id = :userId")
    Optional<RoutineHistory> findRoutineHistory(Long userId, Long routineId, String routineDay);

    @Query("select count(rh) from RoutineHistory rh inner join rh.user u where u.id = :userId and rh.routineDay =:day and rh.routineCheckYn = :routineCheckYN")
    Integer findDayCheckedRoutineNum(Long userId, String day, RoutineCheckYN routineCheckYN);

    @Modifying
    @Query("delete from RoutineHistory rh where rh.routine.id = :routineId")
    void deleteByRoutineId(Long routineId);
}
