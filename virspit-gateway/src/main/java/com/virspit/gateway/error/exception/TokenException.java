package com.virspit.gateway.error.exception;


import com.virspit.gateway.error.ErrorCode;

public class TokenException extends BusinessException{

    public TokenException() {
        super(ErrorCode.TOKEN_NOT_VALID);
    }
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }

}
