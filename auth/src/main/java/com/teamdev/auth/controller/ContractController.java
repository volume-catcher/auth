package com.teamdev.auth.controller;

import com.teamdev.auth.dto.ContractDto;
import com.teamdev.auth.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/licenses/{licenseKey}/products/{productName}")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ContractDto getContractInfo(@PathVariable String licenseKey, @PathVariable String productName) {
        return contractService.getContractInfo(licenseKey, productName);
    }
}
