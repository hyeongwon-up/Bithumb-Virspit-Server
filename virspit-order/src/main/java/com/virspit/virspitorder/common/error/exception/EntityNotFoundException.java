package com.virspit.virspitorder.common.error.exception;


import com.virspit.virspitorder.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
