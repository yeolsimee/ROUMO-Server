package com.yeolsimee.roumo.app.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdDateAt;
    @CreatedDate
    private LocalDate createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDateAt;
    @LastModifiedDate
    private LocalDate lastModifiedDate;
}
