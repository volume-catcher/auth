package com.teamdev.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ContractDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 19, max = 19)
    private String licenseKey;

    @NotNull
    @Size(min = 3, max = 45)
    private String productName;

    @NotNull
    @PositiveOrZero
    private Integer numOfAuthAvailable;

    @NotNull
    @PositiveOrZero
    private Integer remainingNumOfAuthAvailable;

    @NotNull
    private Boolean isActivated;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expireAt;

    @Builder
    public ContractDto(Integer id, String licenseKey, String productName, Integer numOfAuthAvailable, Integer remainingNumOfAuthAvailable, Boolean isActivated, LocalDateTime expireAt) {
        this.id = id;
        this.licenseKey = licenseKey;
        this.productName = productName;
        this.numOfAuthAvailable = numOfAuthAvailable;
        this.remainingNumOfAuthAvailable = remainingNumOfAuthAvailable;
        this.isActivated = isActivated;
        this.expireAt = expireAt;
    }
}
