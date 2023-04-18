package com.yeolsimee.moneysaving.app.routine.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routinehistory.dto.DayResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoutineRepositoryImpl implements RoutineRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public DayResponse findRoutineDay(Long userId, String routineDay, WeekType weekType) {
		return null;
	}
}