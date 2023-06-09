package com.yeolsimee.roumo.app.common.exception;

import com.yeolsimee.roumo.app.common.response.ResponseMessage;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super(ResponseMessage.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(ResponseMessage responseMessage) {
        super(responseMessage);
    }

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ResponseMessage.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(String message, ResponseMessage responseMessage) {
        super(message, responseMessage);
    }
}