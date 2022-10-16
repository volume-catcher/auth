package com.teamdev.auth.exception;

public class InvalidException extends RuntimeException {

    private static final long serialVersionUID = -8268534954869376072L;

    public InvalidException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
