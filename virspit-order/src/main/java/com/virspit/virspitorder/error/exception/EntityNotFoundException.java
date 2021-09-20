package com.virspit.virspitorder.error.exception;


import com.virspit.virspitorder.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
