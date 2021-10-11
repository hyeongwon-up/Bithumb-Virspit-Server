package com.virspit.virspitauth.error.exception;


import com.virspit.virspitauth.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidValueException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
