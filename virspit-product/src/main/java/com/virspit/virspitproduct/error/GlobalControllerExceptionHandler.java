package com.virspit.virspitproduct.error;

import com.virspit.virspitproduct.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    // @RequestBody, @RequestPart binding error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("handleMethodArgumentNotValidException", exception);
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode, exception.getBindingResult()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

    // @ModelAttribute binding error
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        log.error("handleBindException", exception);
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode, exception.getBindingResult()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

    // 404 Error
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        log.error("handleNoHandlerFoundException", exception);
        final ErrorCode errorCode = ErrorCode.NOT_FOUND;
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }

    // Type mismatch exception
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error("handleMethodArgumentTypeMismatchException", exception);
        final ErrorResponse errorResponse = ErrorResponse.of(exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        log.error("handleHttpRequestMethodNotSupportedException", exception);
        final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        log.error("handleHttpMediaTypeNotSupportedException", exception);
        final ErrorCode errorCode = ErrorCode.INVALID_CONTENT_TYPE;
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception) {
        log.error("handleBusinessException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        return new ResponseEntity<>(ErrorResponse.of(exception.getMessage(), errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }
}