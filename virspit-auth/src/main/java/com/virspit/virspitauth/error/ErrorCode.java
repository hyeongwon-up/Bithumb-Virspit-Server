package com.virspit.virspitauth.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "입력값이 올바르지 않습니다"),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않는 HTTP Method 입니다"),
    ENTITY_NOT_FOUND(400, "C003", " 데이터가 존재하지 않습니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류. 이슈를 관리자에게 전달해주세요"),
    INVALID_TYPE_VALUE(400, "C005", "타입이 일치하지 않습니다"),
    ACCESS_DENIED(403, "C006", "접근 거부"),
    NOT_FOUND(404, "C007", "요청을 찾을 수 없습니다"),
    INVALID_CONTENT_TYPE(415, "C008", "Content Type이 올바르지 않습니다"),




    AUTHENTICATION_FAILED(401, "AUTH_001", "AUTHENTICATION_FAILED"),
    LOGIN_FAILED(401, "AUTH_002", "LOGIN_FAILED"),
    BLACKLIST_MEMBER(501, "AUTH_003", "BLACKLIST_MEMBER"),
    TOKEN_GENERATION_FAILED(500, "AUTH_004", "TOKEN_GENERATION_FAILED"),
    EMAIL_ALREADY_EXIST(500, "AUTH_005", "이미 가입한 이메일 입니다."),


    ACCESS_TOKEN_IS_EXPIRED(401, "TOKEN_001", "만료된 ACCESS TOKEN 입니다."),
    TOKEN_IS_NOT_VALID(401, "TOKEN_002", "올바르지 않은 TOKEN 입니다."),
    REFRESH_TOKEN_IS_EXPIRED(401, "TOKEN_003", "만료된 REFRESH TOKEN 입니다.");


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