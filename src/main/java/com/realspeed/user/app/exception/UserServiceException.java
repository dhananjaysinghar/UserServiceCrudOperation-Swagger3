package com.realspeed.user.app.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

    private final InternalStandardError error;

    public UserServiceException(InternalStandardError error) {
        this.error = error;
    }
}
