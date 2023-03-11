package com.yeolsimee.moneysaving.app.category;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
