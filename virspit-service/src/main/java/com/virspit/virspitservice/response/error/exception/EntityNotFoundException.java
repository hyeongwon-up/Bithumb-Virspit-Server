package com.virspit.virspitservice.response.error.exception;


import com.virspit.virspitservice.response.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
