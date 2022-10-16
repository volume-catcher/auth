package com.teamdev.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class AuthRequestDto {

    @NotNull
    @Size(min = 19, max = 19)
    private String licenseKey;

    private String productName;

    @NotNull
    private UUID device;

    @Builder
    public AuthRequestDto(String licenseKey, String productName, UUID device) {
        this.licenseKey = licenseKey;
        this.productName = productName;
        this.device = device;
    }
}
