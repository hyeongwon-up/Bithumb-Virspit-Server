package com.virspit.virspitproduct.domain.sports.exception;

import com.virspit.virspitproduct.error.ErrorCode;
import com.virspit.virspitproduct.error.exception.BusinessException;

public class NameDuplicatedException extends BusinessException {
    public NameDuplicatedException() {
        super(ErrorCode.DUPLICATED_SPORTS_NAME);
    }
}
