package com.realspeed.user.app.exception;

import com.realspeed.user.app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceException exception) {
        InternalStandardError error = exception.getError();
        log.error("Error Occurred {}", error.getErrorMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(error.getErrorCode())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(error.getErrorMessage())
                .build(), error.getStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("Error Occurred {}", exception.getMessage());
        return ErrorResponse.builder()
                .errorCode(InternalStandardError.INVALID_INPUT.getErrorCode())
                .message(InternalStandardError.INVALID_INPUT.getErrorMessage())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .description(exception.getConstraintViolations().iterator().next().getMessageTemplate())
                .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Error Occurred {}", exception.getMessage());
        return ErrorResponse.builder()
                .errorCode(InternalStandardError.INVALID_INPUT.getErrorCode())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(InternalStandardError.INVALID_INPUT.getErrorMessage())
                .description(exception.getBindingResult().getAllErrors().iterator().next().getDefaultMessage())
                .build();
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        log.error("Error Occurred {}", exception.getMessage());
        log.error("Error StackTrace {}", Arrays.toString(exception.getStackTrace()));
        return ErrorResponse.builder()
                .errorCode(InternalStandardError.INTERNAL_SERVER_ERROR.getErrorCode())
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .message(InternalStandardError.INTERNAL_SERVER_ERROR.getErrorMessage())
                .description(exception.getMessage())
                .build();
    }
}
