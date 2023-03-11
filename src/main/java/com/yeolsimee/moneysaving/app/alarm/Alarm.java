package com.yeolsimee.moneysaving.app.alarm;

import com.yeolsimee.moneysaving.app.common.entity.*;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alarmDay;

    private String alarmTime;
}
