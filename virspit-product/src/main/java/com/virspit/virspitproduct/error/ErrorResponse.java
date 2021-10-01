package com.virspit.virspitproduct.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "ErrorResponse", description = "오류")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {
    @ApiModelProperty("오류 메시지")
    private String message;
    
    @ApiModelProperty("HTTP 상태 코드")
    private int status;
    
    @ApiModelProperty("오류 상세 정보")
    private List<FieldError> errors;
    
    @ApiModelProperty("서비스 오류 코드")
    private String code;

    public ErrorResponse(String message, int status, List<FieldError> errors, String code) {
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.code = code;
    }

    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> errors) {
        this(errorCode.getMessage(), errorCode.getStatus(), errors, errorCode.getCode());
    }

    private ErrorResponse(final String message, final ErrorCode errorCode) {
        this(message, errorCode.getStatus(), new ArrayList<>(), errorCode.getCode());
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode);
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode) {
        return new ErrorResponse(message, errorCode);
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        @ApiModelProperty("오류 필드")
        private String field;

        @ApiModelProperty("오류 값")
        private String value;

        @ApiModelProperty("오류 사유")
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
