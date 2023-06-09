package com.yeolsimee.roumo.app.user.entity;

import lombok.*;

/**
 * packageName    : com.yeolsimee.roumo.app.user
 * fileName       : Role
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */

@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_USER("ROLE_USER"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;
}