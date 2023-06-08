package com.yeolsimee.roumo.app.common.exception;

import com.yeolsimee.roumo.app.common.response.ResponseMessage;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super(ResponseMessage.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message, ResponseMessage.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ResponseMessage responseMessage)  { super(responseMessage); }
}

