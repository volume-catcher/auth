package com.teamdev.auth.service;

import com.teamdev.auth.dto.ContractDto;
import com.teamdev.auth.exception.ErrorMessage;
import com.teamdev.auth.exception.InvalidException;
import com.teamdev.auth.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractDto getContractInfo(String licenseKey, String productName) {
        ContractDto contractDto = contractRepository.getContractInfoQ(licenseKey, productName);

        if (contractDto == null) {
            throw new InvalidException(ErrorMessage.CONTRACT_INVALID);
        }

        return contractDto;
    }

}
