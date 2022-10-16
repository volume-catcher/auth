package com.teamdev.auth.controller;

import com.teamdev.auth.dto.AuthDto;
import com.teamdev.auth.dto.AuthRequestDto;
import com.teamdev.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AuthDto createAuth(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return authService.createAuth(authRequestDto);
    }

    @GetMapping
    public AuthDto getAuth(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return authService.getAuth(authRequestDto);
    }
}
