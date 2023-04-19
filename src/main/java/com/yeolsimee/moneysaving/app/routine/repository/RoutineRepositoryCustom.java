package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.DayResponse;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;

public interface RoutineRepositoryCustom {
	DayResponse findRoutineDay(Long userId, String routineDay, WeekType weekType);
}
