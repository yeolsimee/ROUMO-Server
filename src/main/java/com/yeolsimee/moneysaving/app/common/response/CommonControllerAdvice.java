package com.yeolsimee.moneysaving.app.common.response;

import com.yeolsimee.moneysaving.app.common.exception.BaseException;
import com.yeolsimee.moneysaving.app.common.response.service.ResponseService;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class CommonControllerAdvice {

    private final ResponseService responseService;

    /**
     * 비즈니스 로직 처리에서 에러가 발생함
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    public CommonResult onBaseException(BaseException e) {
        log.error("[BaseException] cause = {}, errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return responseService.getFailResult(400, e.getMessage());
    }

    /**
     * valid 예외 체크
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("[BaseException] errorMsg = {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        errors.put("message", "요청 필드의 유효성 검사를 실패하였습니다.");
        return responseService.getFailResult(400, errors.toString());
    }

    @ResponseBody
    @ExceptionHandler(value = ExpiredJwtException.class)
    public CommonResult ExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage());
        return responseService.getFailResult(401, ResponseMessage.EXPIRED_JWT_TOKEN.getMessage());

    }

    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResult onException(Exception e) {
        log.error("eventId = {} ", e);
        return responseService.getFailResult(400, ResponseMessage.COMMON_SYSTEM_ERROR.getMessage());
    }
}
