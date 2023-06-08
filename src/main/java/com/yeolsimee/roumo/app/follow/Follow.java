package com.yeolsimee.roumo.app.follow;

import com.yeolsimee.roumo.app.common.entity.*;
import com.yeolsimee.roumo.app.user.entity.*;
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
