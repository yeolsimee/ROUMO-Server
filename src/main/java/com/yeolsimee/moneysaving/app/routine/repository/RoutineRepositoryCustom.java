package com.yeolsimee.moneysaving.app.routine.repository;

import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.DayResponse;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.RoutineData;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;

import java.util.List;

public interface RoutineRepositoryCustom {
	DayResponse findRoutineDay(Long userId, String date, WeekType weekType, String checkedRoutineShow);
	List<RoutineData> findRoutineDatas(Long categoryId, String date, WeekType weekType, String checkedRoutineShow);
}
