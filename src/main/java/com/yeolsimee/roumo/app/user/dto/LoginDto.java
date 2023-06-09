package com.yeolsimee.roumo.app.user.dto;

import lombok.*;

/**
 * packageName    : com.yeolsimee.roumo.app.user.dto
 * fileName       : LoginDto
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */

@Data
@Builder
public class LoginDto {

    private String name;

    private String isNewUser;
}
