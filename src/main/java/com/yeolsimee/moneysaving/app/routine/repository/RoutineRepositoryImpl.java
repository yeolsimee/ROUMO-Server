package com.yeolsimee.moneysaving.app.routine.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.QRoutineData;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.RoutineData;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineCheckYN;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yeolsimee.moneysaving.app.routine.entity.QRoutine.routine;
import static com.yeolsimee.moneysaving.app.routinehistory.entity.QRoutineHistory.routineHistory;
import static com.yeolsimee.moneysaving.app.user.entity.QUser.user;

@RequiredArgsConstructor
public class RoutineRepositoryImpl implements RoutineRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<RoutineData> findRoutineDay(Long userId, String routineDay, WeekType weekType) {

		Expression<String> routineCheckYnType = new CaseBuilder()
				.when(routineHistory.routineCheckYn.eq(RoutineCheckYN.Y)).then("Y")
				.otherwise(Expressions.asString("N"));

		JPQLQuery<String> routineCheckYN = JPAExpressions
				.select(routineCheckYnType)
				.from(routineHistory)
				.where(
						routineHistory.routine.eq(routine),
						routineHistory.routineDay.eq(routineDay)
				);

		List<RoutineData> fetch = queryFactory
				.select(new QRoutineData(
						routine.id.stringValue(),
						routine.routineName,
						Expressions.stringTemplate(
								"coalesce({0}, 'N')",
								routineCheckYN
						),
						routine.routineTimeZone.stringValue(), //todo: 숫자로 변경 필요
						routine.alarmTime.substring(0, 2),
						routine.alarmTime.substring(2, 4)
				))
				.from(routine)
				.join(routine.user, user)
				.where(
						user.id.eq(userId),
						routine.routineStartDate.loe(routineDay),
						routine.routineEndDate.goe(routineDay),
						Expressions.anyOf(routine.weekTypes.any().eq(weekType),
								routine.weekTypes.isEmpty())
				)
				.fetch();

		return fetch;
	}
}