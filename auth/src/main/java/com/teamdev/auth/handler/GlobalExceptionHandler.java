package com.teamdev.auth.handler;

import com.teamdev.auth.dto.ErrorDto;
import com.teamdev.auth.exception.ForbiddenException;
import com.teamdev.auth.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorDto handleForbiddenException(HttpServletRequest req, ForbiddenException e) {
        return new ErrorDto(e.getMessage(), LocalDateTime.now(), req.getRequestURL().toString());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(HttpServletRequest req, NotFoundException e) {
        return new ErrorDto(e.getMessage(), LocalDateTime.now(), req.getRequestURL().toString());
    }
}
