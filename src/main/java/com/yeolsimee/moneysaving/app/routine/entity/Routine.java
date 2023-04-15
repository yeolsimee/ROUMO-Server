package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.category.entity.Category;
import com.yeolsimee.moneysaving.app.common.entity.BaseEntity;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routineday.entity.RoutineDay;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String routineName;
    private String routineDeleteYN;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<WeekType> weekTypes;
    @Enumerated(EnumType.STRING)
    private RoutineType routineType;
    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;
    private String alarmTime;
    private String routineStartDate;
    private String routineEndDate;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private RoutineTimeZone routineTimeZone;

    @Builder
    public Routine(User user, String routineName, Category category, List<WeekType> weekTypes, RoutineType routineType, AlarmStatus alarmStatus, String alarmTime, String routineStartDate, String routineEndDate, RoutineTimeZone routineTimeZone, String routineDeleteYN) {
        this.user = user;
        this.routineName = routineName;
        this.category = category;
        this.weekTypes = weekTypes;
        this.routineType = routineType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = alarmTime;
        this.routineStartDate = routineStartDate;
        this.routineEndDate = routineEndDate;
        this.routineTimeZone = routineTimeZone;
        this.routineDeleteYN = routineDeleteYN;
    }

    public void updateRoutine(RoutineRequest routineRequest, Category category) {
        List<WeekType> weekTypeList = routineRequest.getWeekTypes().stream()
                .map(s -> WeekType.valueOf(s))
                .collect(Collectors.toList());

        this.routineName = routineRequest.getRoutineName();
        this.category = category;
        this.weekTypes = weekTypeList;
        this.routineType = RoutineType.valueOf(routineRequest.getRoutineType());
        this.alarmStatus = AlarmStatus.valueOf(routineRequest.getAlarmStatus());
        this.alarmTime = routineRequest.getAlarmTime();
        this.routineTimeZone = RoutineTimeZone.idOfRoutineTimeZone(routineRequest.getRoutineTimeZone());
    }

    public void changeRoutineDeleteYN(String routineDeleteYN) {
        this.routineDeleteYN = routineDeleteYN;
    }

    public void changeEndDate(String day) {
        this.routineEndDate = day;
    }
}


