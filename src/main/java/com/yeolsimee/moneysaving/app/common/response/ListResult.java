package com.yeolsimee.moneysaving.app.common.response;

import lombok.*;

import java.util.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common.response
 * fileName       : ListResult
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
public class ListResult<T> extends CommonResult{

    private List<T> data;
}
