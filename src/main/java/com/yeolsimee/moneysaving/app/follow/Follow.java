package com.yeolsimee.moneysaving.app.follow;

import com.yeolsimee.moneysaving.app.common.entity.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Getter
@Entity
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    @OneToMany(targetEntity = User.class)
    private List<User> follower;
}
