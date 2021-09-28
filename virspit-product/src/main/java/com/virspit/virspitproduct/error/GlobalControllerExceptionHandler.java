package com.virspit.virspitproduct.error;

import com.virspit.virspitproduct.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, exception.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    // @ModelAttribute binding error
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
        log.error("handleBindException", exception);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, exception.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    // 404 Error
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        log.error("handleNoHandlerFoundException", exception);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.NO_HANDLER_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error("handleMethodArgumentTypeMismatchException", exception);
        return new ResponseEntity<>(ErrorResponse.of(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        log.error("handleHttpRequestMethodNotSupportedException", exception);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception) {
        log.error("handleBusinessException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        return new ResponseEntity<>(ErrorResponse.of(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("handleException", exception);
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}