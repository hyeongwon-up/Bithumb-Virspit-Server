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

    private SuccessResponse(final T data) {
        message = "success";
        status = HttpStatus.OK.value();
        this.data = data;
    }

    public static <T> SuccessResponse of(final T data) {
        return new SuccessResponse<>(data);
    }
}