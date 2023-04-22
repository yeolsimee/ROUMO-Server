package com.yeolsimee.moneysaving.app.routine.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yeolsimee.moneysaving.app.routine.dto.dayresponse.*;
import com.yeolsimee.moneysaving.app.routine.entity.WeekType;
import com.yeolsimee.moneysaving.app.routinehistory.entity.RoutineCheckYN;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yeolsimee.moneysaving.app.category.entity.QCategory.category;
import static com.yeolsimee.moneysaving.app.routine.entity.QRoutine.routine;
import static com.yeolsimee.moneysaving.app.routinehistory.entity.QRoutineHistory.routineHistory;
import static com.yeolsimee.moneysaving.app.user.entity.QUser.user;

@RequiredArgsConstructor
public class RoutineRepositoryImpl implements RoutineRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DayResponse findRoutineDay(Long userId, String date, WeekType weekType, String checkedRoutineShow) {

        List<CategoryData> categoryData = queryFactory.select(new QCategoryData(
                        category.id,
                        category.categoryName)
                ).from(category)
                .leftJoin(routine).on(category.id.eq(routine.category.id))
                .join(routine.user, user)
                .leftJoin(routineHistory).on(routineHistory.routine.id.eq(routine.id))
                .groupBy(category)
                .where(
                        user.id.eq(userId),
                        routine.routineStartDate.loe(date),
                        routine.routineEndDate.goe(date),
                        checkedRoutineShow.equals("N") ? routineHistory.routineCheckYn.isNull().or(routineHistory.routineCheckYn.eq(RoutineCheckYN.N)) : null,
                        Expressions.anyOf(routine.weekTypes.any().eq(weekType),
                                routine.weekTypes.isEmpty())
                )
                .fetch();

        for (CategoryData categoryData1 : categoryData) {
            categoryData1.setRoutineDatas(findRoutineDatas(categoryData1.getCategoryId(), date, weekType, checkedRoutineShow));
        }

        return DayResponse.of(date, categoryData);
    }

    @Override
    public List<RoutineData> findRoutineDatas(Long categoryId, String date, WeekType weekType, String checkedRoutineShow) {
        Expression<String> routineCheckYnType = new CaseBuilder()
                .when(routineHistory.routineCheckYn.eq(RoutineCheckYN.Y)).then("Y")
                .otherwise(Expressions.asString("N"));

        JPQLQuery<String> routineCheckYN = JPAExpressions
                .select(routineCheckYnType)
                .from(routineHistory)
                .where(
                        routineHistory.routine.eq(routine),
                        routineHistory.routineDay.eq(date)
                );

        return queryFactory.select(new QRoutineData(
                        routine.id,
                        routine.routineName,
                        Expressions.stringTemplate(
                                "coalesce({0}, 'N')",
                                routineCheckYN
                        ),
                        routine.routineTimeZone,
                        routine.alarmTime.substring(0, 2),
                        routine.alarmTime.substring(2, 4)
                ))
                .from(category)
                .leftJoin(routine).on(category.id.eq(routine.category.id))
                .leftJoin(routineHistory).on(routineHistory.routine.id.eq(routine.id))
                .where(
                        category.id.eq(categoryId),
                        routine.routineStartDate.loe(date),
                        routine.routineEndDate.goe(date),
                        checkedRoutineShow.equals("N") ? routineHistory.routineCheckYn.isNull().or(routineHistory.routineCheckYn.eq(RoutineCheckYN.N)) : null,
                        Expressions.anyOf(routine.weekTypes.any().eq(weekType),
                                routine.weekTypes.isEmpty()))
                .fetch();
    }
}