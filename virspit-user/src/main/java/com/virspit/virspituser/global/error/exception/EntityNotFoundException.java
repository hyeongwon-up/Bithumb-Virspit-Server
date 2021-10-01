package com.virspit.virspituser.global.error.exception;


import com.virspit.virspituser.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String target) {
        super(target + ErrorCode.ENTITY_NOT_FOUND.getMessage(), ErrorCode.ENTITY_NOT_FOUND);
    }
}
