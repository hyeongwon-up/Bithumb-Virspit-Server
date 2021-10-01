package com.virspit.virspituser.global.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
    public static final String SUCCESS_MESSAGE = "success";
    public static final String DELETED_MESSAGE = "deleted";

    @ApiModelProperty("메시지")
    private String message;
    @ApiModelProperty("HTTP 상태 코드")
    private int status;
    @ApiModelProperty("응답 데이터")
    private T data;

    private SuccessResponse(final String message, final T data, final HttpStatus httpStatus) {
        this.message = message;
        status = httpStatus.value();
        this.data = data;
    }

    public static <T> SuccessResponse<T> of(final T data) {
        return new SuccessResponse<>(SUCCESS_MESSAGE, data, HttpStatus.OK);
    }

    public static <T> SuccessResponse<T> of(final T data, final HttpStatus httpStatus) {
        return new SuccessResponse<>(SUCCESS_MESSAGE, data, httpStatus);
    }

    public static <T> SuccessResponse<T> of(final T data, final String message) {
        return new SuccessResponse<>(message, data, HttpStatus.OK);
    }
}