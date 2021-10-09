package com.realspeed.user.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum InternalStandardError {

    INVALID_INPUT("Invalid Input", HttpStatus.BAD_REQUEST, "4000"),
    USER_NOT_FOUND("User Not exist", HttpStatus.NOT_FOUND, "4001"),
    USER_ALREADY_EXIST("User already exist", HttpStatus.CONFLICT, "4002"),
    USER_DEACTIVATED("User is not active", HttpStatus.BAD_REQUEST, "4003"),
    INTERNAL_SERVER_ERROR("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, "5000");

    private final String errorMessage;
    private final HttpStatus status;
    private final String errorCode;
}
