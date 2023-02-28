package com.yeolsimee.moneysaving.app.common.response;

import lombok.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common.response
 * fileName       : CommonResponse
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
public enum CommonResponse {
    SUCCESS(0, "성공하였습니다."), FAIL(-1, "실패하였습니다.");

    private final int code;
    private final String message;

}
