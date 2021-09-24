package com.virspit.virspitservice.domain.response.error;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GlobalException {
    private static final long SERIAL_VERSION_UID = -1L;

    public UnauthorizedException(HttpStatus status) {
        super(status);
    }

    public UnauthorizedException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public UnauthorizedException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
