package com.virspit.virspitorder.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE( 400, "C001", "Invalid input value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method not allowed"),
    ENTITY_NOT_FOUND(400, "C003", "Entity not found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server error"),
    INVALID_TYPE_VALUE(400, "C005", "Invalid type value"),
    ACCESS_DENIED(403, "C006", "Access is denied"),
    NO_HANDLER_FOUND(404, "C007", "Not found"),

    // TODO: 각 도메인에서 발생할 오류에 대한 코드 추가
    // Member
    EMAIL_DUPLICATION(400, "M001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "M002", "Login input is invalid"),

    // Coupon
    COUPON_ALREADY_USE(400, "CO001", "Coupon was already used"),
    COUPON_EXPIRE(400, "CO002", "Coupon was already expired");

    @Getter
    private final int status;
    @Getter
    private final String code;
    @Getter
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
