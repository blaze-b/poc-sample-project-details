package com.mclebtec.handler;

import com.mclebtec.handler.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice

public class ErrorHandler {

    public static final String ERROR_CODE = "ERROR_CODE";

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorDto<String>> handleLuminException(GenericException exception) {
        final ErrorDto<String> errorDto = new ErrorDto<>(HttpStatus.BAD_REQUEST, exception);
        MDC.put(ERROR_CODE, errorDto.getErrorCode());
        log.error("\"message\":\"%s\"".formatted(exception.getMessage()), exception.getCause());
        MDC.remove(ERROR_CODE);
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDto<String>> handleGenericException(Exception exception) {
        final ErrorDto<String> errorDto = new ErrorDto<>(HttpStatus.BAD_REQUEST);
        MDC.put(ERROR_CODE, errorDto.getErrorCode());
        log.error("\"message\":\"%s\"".formatted(exception.getMessage()), exception.getCause());
        MDC.remove(ERROR_CODE);
        return ResponseEntity.badRequest().body(errorDto);
    }

}
