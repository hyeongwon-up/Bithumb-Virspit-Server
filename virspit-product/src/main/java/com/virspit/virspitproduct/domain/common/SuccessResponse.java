package com.virspit.virspitproduct.domain.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
    @ApiModelProperty("메시지")
    private String message;
    @ApiModelProperty("HTTP 상태 코드")
    private int status;
    @ApiModelProperty("응답 데이터")
    private T data;

    private SuccessResponse(final T data, final HttpStatus httpStatus) {
        message = "success";
        status = httpStatus.value();
        this.data = data;
    }

    private SuccessResponse(final T data) {
        this(data, HttpStatus.OK);
    }

    public static <T> SuccessResponse<T> of(final T data) {
        return new SuccessResponse<>(data);
    }

    public static <T> SuccessResponse<T> of(final T data, final HttpStatus httpStatus) {
        return new SuccessResponse<>(data);
    }
}