package com.teamdev.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    CONTRACT_INVALID("유효하지 않은 계약입니다"),
    AUTH_INVALID("유효하지 않은 인증입니다");

    private final String message;
}
