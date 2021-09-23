package com.virspit.virspitservice.domain.response.error.exception;


import com.virspit.virspitservice.domain.response.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
