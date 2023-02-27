package com.yeolsimee.moneysaving.app.common.response;

import lombok.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common.response
 * fileName       : ResponseMessage
 * author         : jeon-eunseong
 * date           : 2023/02/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/26        jeon-eunseong       최초 생성
 */
@Getter
@AllArgsConstructor
public enum ResponseMessage {

    AUTH_USER("사용자 정보가 없습니다.");

    final String message;
}
