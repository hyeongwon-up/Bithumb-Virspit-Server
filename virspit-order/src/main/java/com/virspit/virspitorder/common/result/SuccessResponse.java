package com.virspit.virspitorder.common.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponse<T> {
    private String message;
    private int status;
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