package com.yeolsimee.moneysaving.app.common.bean;

import lombok.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common
 * fileName       : ResultBean
 * author         : jeon-eunseong
 * date           : 2023/02/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/26        jeon-eunseong       최초 생성
 */
@Getter
@Setter
public class ResultBean<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;


}
