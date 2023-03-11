package com.yeolsimee.moneysaving.app.routine.entity;

import com.yeolsimee.moneysaving.app.common.entity.*;
import lombok.*;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String routineName;

    private String routineAlarmYn;

    private String routinePublicYn;

}
