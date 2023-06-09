package com.yeolsimee.roumo.app.common.response;

import lombok.*;

/**
 * packageName    : com.yeolsimee.roumo.app.common.response
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
    // 공통 에러메세지
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // USER 에러코드
    AUTH_USER("사용자 정보가 없습니다."),
    WITHDRAW_USER("탈퇴한 회원입니다."),

    // ROUTINE 에러코드
    NOT_VALID_ROUTINE("해당 루틴이 없습니다."),

    // ROUTINEDAY 에러코드
    NOT_VALID_ROUTINE_DAY("해당 루틴데이가 없습니다."),
    ROUTINE_DAY_TIME_LENGTH_NOT_FOUR("해당 루틴데이의 시간 숫자가 4개가 아닙니다."),

    // Category 에러코드
    NOT_VALID_CATEGORY("해당 카테고리가 없습니다."),

    // Util 에러코드
    NOT_PARSE_WEEKTYPE("요일값으로 parsing이 안됩니다."),

    //JWT 에러코드
    EMPTY_JWT_TOKEN("토큰이 없습니다."),
    EXPIRED_JWT_TOKEN("만료된 토큰입니다."),
    INVALID_ID_TOKEN("유효하지 않은 토큰입니다."),


    ;
    final String message;
}
