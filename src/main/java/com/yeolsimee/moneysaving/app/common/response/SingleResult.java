package com.yeolsimee.moneysaving.app.common.response;

import lombok.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common.response
 * fileName       : SingleResult
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
public class SingleResult<T> extends CommonResult{
    private T data;

}
