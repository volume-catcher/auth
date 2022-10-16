package com.teamdev.auth.service;

import com.teamdev.auth.dto.AuthDto;
import com.teamdev.auth.dto.AuthRequestDto;
import com.teamdev.auth.entity.AuthEntity;
import com.teamdev.auth.entity.ContractEntity;
import com.teamdev.auth.exception.ErrorMessage;
import com.teamdev.auth.exception.InvalidException;
import com.teamdev.auth.repository.AuthRepository;
import com.teamdev.auth.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthRepository authRepository;
    private final ContractRepository contractRepository;

    public AuthDto createAuth(AuthRequestDto authRequestDto) {
        ContractEntity contractEntity = contractRepository
                .findOneContractByLicenseKeyAndProductNameQ(
                        authRequestDto.getLicenseKey(), authRequestDto.getProductName()
                );

        if (!isValidContract(contractEntity)) {
            throw new InvalidException(ErrorMessage.CONTRACT_INVALID);
        }

        //TODO: return jwt
        return null;
    }

    public AuthDto getAuth(AuthRequestDto authRequestDto) {
        ContractEntity contractEntity = contractRepository
                .findOneContractByLicenseKeyAndProductNameQ(
                        authRequestDto.getLicenseKey(), authRequestDto.getProductName()
                );

        if (!isValidContract(contractEntity)) {
            throw new InvalidException(ErrorMessage.CONTRACT_INVALID);
        }

        AuthEntity authEntity = authRepository
                .findOneAuthByDeviceAndContractIdQ(
                        authRequestDto.getDevice(), contractEntity.getId()
                );

        if (!isValidAuth(authEntity, authRequestDto.getDevice())) {
            throw new InvalidException(ErrorMessage.AUTH_INVALID);
        }

        //TODO: return jwt
        return null;
    }

    private boolean isValidContract(ContractEntity contractEntity) {
        if (contractEntity != null) {
            return contractEntity.getIsActivated()
                    && contractEntity.getExpireAt().isAfter(LocalDateTime.now());
        }

        return false;
    }

    private boolean isValidAuth(AuthEntity authEntity, UUID device) {
        if (authEntity != null) {
            return authEntity.getIsActivated() && authEntity.getDevice().equals(device);
        }

        return false;
    }
}
