package com.teamdev.auth.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5854347137722224858L;

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
