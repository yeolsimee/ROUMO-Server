package com.yeolsimee.moneysaving.app.common.exception;

import com.yeolsimee.moneysaving.app.common.response.ResponseMessage;

public class IllegalStatusException extends BaseException {

    public IllegalStatusException() {
        super(ResponseMessage.COMMON_ILLEGAL_STATUS);
    }

    public IllegalStatusException(String message) {
        super(message, ResponseMessage.COMMON_ILLEGAL_STATUS);
    }

    public IllegalStatusException(ResponseMessage responseMessage)  { super(responseMessage); }
}

