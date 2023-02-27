package com.yeolsimee.moneysaving.app.common.response.service;

import com.yeolsimee.moneysaving.app.common.response.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * packageName    : com.yeolsimee.moneysaving.app.common.response.service
 * fileName       : ResponseService
 * author         : jeon-eunseong
 * date           : 2023/02/26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/26        jeon-eunseong       최초 생성
 */
@Service("responseService")
public class ResponseService {

    // 단일건 결과 처리 메소드
    public <T> SingleResult<T> getSingleResult(final T data) {
        final SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 복수건 결과 처리 메서드
    public <T> ListResult<T> getListResult(final List<T> list) {
        final ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과만 처리
    public CommonResult getSuccessResult() {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult(final String msg) {
        final CommonResult result = new CommonResult();
        setSuccessResult(result);
        setSuccessResult(result, msg);
        return result;
    }

    // 실패 결과만 처리
    public CommonResult getFailResult() {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result);
        return result;
    }

    public CommonResult getFailResult(final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, -1, msg);
        return result;
    }

    public CommonResult getFailResult(final int code, final String msg) {
        final CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, code, msg);
        return result;
    }

    // API 요청 성공 시 응답 모델을 성공 데이터로 세팅
    private void setSuccessResult(final CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMessage());
    }

    private void setSuccessResult(final CommonResult result, final String msg) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(msg);
    }

    private void setFailResult(final CommonResult result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMessage(CommonResponse.FAIL.getMessage());
    }

    // API 요청 실패 시 응답 모델을 실패 데이터로 세팅
    private void setFailResult(final CommonResult result, final int code, final String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(msg);
    }

}