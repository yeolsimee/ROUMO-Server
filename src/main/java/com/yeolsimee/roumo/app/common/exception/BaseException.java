package com.yeolsimee.roumo.app.common.exception;

import com.yeolsimee.roumo.app.common.response.ResponseMessage;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private ResponseMessage responseMessage;

    public BaseException() {
    }

    public BaseException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.responseMessage = responseMessage;
    }

    public BaseException(String message, ResponseMessage responseMessage) {
        super(message);
        this.responseMessage = responseMessage;
    }

    public BaseException(String message, ResponseMessage responseMessage, Throwable cause) {
        super(message, cause);
        this.responseMessage = responseMessage;
    }
}
