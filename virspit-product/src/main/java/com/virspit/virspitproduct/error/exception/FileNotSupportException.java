package com.virspit.virspitproduct.error.exception;

import com.virspit.virspitproduct.error.ErrorCode;

public class FileNotSupportException extends BusinessException {
    public FileNotSupportException() {
        super(ErrorCode.FILE_NOT_SUPPORT);
    }
}
