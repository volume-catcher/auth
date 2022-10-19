package com.teamdev.auth.controller;

import com.teamdev.auth.dto.AuthDto;
import com.teamdev.auth.dto.AuthRequestDto;
import com.teamdev.auth.jwt.TokenProvider;
import com.teamdev.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public AuthDto getAuth(HttpServletRequest request, @Valid @RequestBody AuthRequestDto authRequestDto) {
        String token = tokenProvider.getToken(request);

        if (token == null) {
            return authService.createAuth(authRequestDto);
        } else {
            return authService.getAuth(authRequestDto, token);
        }
    }
}
