package com.teamdev.auth.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -8268534954869376072L;

    public ForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
