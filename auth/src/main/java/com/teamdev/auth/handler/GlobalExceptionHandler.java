package com.teamdev.auth.handler;

import com.teamdev.auth.dto.ErrorDto;
import com.teamdev.auth.exception.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(HttpServletRequest req, InvalidException e) {
        return new ErrorDto(e.getMessage(), LocalDateTime.now(), req.getRequestURL().toString());
    }
}
