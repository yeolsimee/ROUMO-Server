package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routinehistory.dto.DayResponse;

public interface RoutineRepositoryCustom {
	DayResponse findRoutineDay(Long userId, String routineDay, WeekType weekType);
}
