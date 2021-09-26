package com.virspit.virspitservice.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalException extends ResponseStatusException {
    private static final long SERIAL_VERSION_UID = -1L;

    public GlobalException(HttpStatus status) {
        super(status);
    }

    public GlobalException(HttpStatus status, String reason) {
        super(status, reason);
    }
    public GlobalException(String reason, ErrorCode code) {
        super(code.getHttpStatus(), reason);
    }

    public GlobalException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
