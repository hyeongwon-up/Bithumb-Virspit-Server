package com.virspit.virspitproduct.domain.sports.exception;

import com.virspit.virspitproduct.error.ErrorCode;
import com.virspit.virspitproduct.error.exception.BusinessException;

public class IconFileNotFoundException extends BusinessException {
    public IconFileNotFoundException() {
        super(ErrorCode.ICON_FILE_NOT_FOUND);
    }
}
