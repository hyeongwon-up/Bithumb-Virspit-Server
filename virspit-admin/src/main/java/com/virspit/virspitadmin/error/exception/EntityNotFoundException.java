package com.virspit.virspitadmin.error.exception;

import com.virspit.virspitadmin.error.ErrorCode;
import com.virspit.virspitadmin.error.exception.BusinessException;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
