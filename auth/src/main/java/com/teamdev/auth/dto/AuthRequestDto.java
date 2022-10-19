package com.teamdev.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class AuthRequestDto {

    @NotNull
    @Size(min = 19, max = 19)
    private String licenseKey;

    private String productName;

    @NotNull
    @Size(min = 36, max = 36)
    private String device;

    @Builder
    public AuthRequestDto(String licenseKey, String productName, String device) {
        this.licenseKey = licenseKey;
        this.productName = productName;
        this.device = device;
    }
}
