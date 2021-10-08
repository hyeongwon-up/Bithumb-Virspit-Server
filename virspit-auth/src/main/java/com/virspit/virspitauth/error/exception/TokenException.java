package com.virspit.virspitauth.error.exception;


import com.virspit.virspitauth.error.ErrorCode;

public class TokenException extends BusinessException{

    public TokenException() {
        super(ErrorCode.TOKEN_IS_NOT_VALID);
    }
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public
    TokenException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }

}
