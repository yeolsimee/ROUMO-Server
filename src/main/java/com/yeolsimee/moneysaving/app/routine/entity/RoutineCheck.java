package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.Id;
import javax.persistence.*;

@Getter
@Entity
public class RoutineCheck extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String routineCheckDay;

    @CreatedDate
    private String routineCheckYn;

}
