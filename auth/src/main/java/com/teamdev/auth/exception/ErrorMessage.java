package com.teamdev.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    CONTRACT_FORBIDDEN("유효하지 않은 계약입니다"),
    AUTH_FORBIDDEN("유효하지 않은 인증입니다"),
    EXCEED_NUM_OF_AUTH_AVAILABLE_FORBIDDEN("인증 가능한 횟수를 초과하였습니다"),

    CONTRACT_NOT_FOUND("존재하지 않는 계약입니다"),
    AUTH_NOT_FOUND("존재하지 않는 인증입니다"),

    DEVICE_FORBIDDEN("잘못된 장치입니다"),
    ;

    private final String message;
}
