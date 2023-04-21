package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.RoutineData;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;

import java.util.List;

public interface RoutineRepositoryCustom {
	List<RoutineData> findRoutineDay(Long userId, String routineDay, WeekType weekType);
}
