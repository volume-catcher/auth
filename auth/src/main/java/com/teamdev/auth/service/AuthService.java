package com.teamdev.auth.service;

import com.teamdev.auth.dto.AuthDto;
import com.teamdev.auth.dto.AuthRequestDto;
import com.teamdev.auth.entity.AuthEntity;
import com.teamdev.auth.entity.ContractEntity;
import com.teamdev.auth.exception.ErrorMessage;
import com.teamdev.auth.exception.ForbiddenException;
import com.teamdev.auth.exception.NotFoundException;
import com.teamdev.auth.jwt.TokenProvider;
import com.teamdev.auth.repository.AuthRepository;
import com.teamdev.auth.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthRepository authRepository;
    private final ContractRepository contractRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public AuthDto createAuth(AuthRequestDto authRequestDto) {
        ContractEntity contractEntity = contractRepository
                .findOneContractByLicenseKeyAndProductNameQ(
                        authRequestDto.getLicenseKey(), authRequestDto.getProductName());

        if (contractEntity == null) {
            throw new NotFoundException(ErrorMessage.CONTRACT_NOT_FOUND);
        }

        if (!isValidContract(contractEntity)) {
            throw new ForbiddenException(ErrorMessage.CONTRACT_FORBIDDEN);
        }

        AuthEntity authEntity = authRepository
                .findOneAuthByDeviceAndContractIdQ(
                        authRequestDto.getDevice(), contractEntity.getId()
                );

        if (authEntity == null) {
            Long numOfAuth = authRepository.findNumOfAuthByContractIdQ(contractEntity.getId());

            if (numOfAuth >= contractEntity.getNumOfAuthAvailable()) {
                throw new ForbiddenException(ErrorMessage.EXCEED_NUM_OF_AUTH_AVAILABLE_FORBIDDEN);
            }

            AuthEntity newAuthEntity = AuthEntity.builder()
                    .device(authRequestDto.getDevice())
                    .isActivated(true)
                    .contract(contractEntity)
                    .build();

            authRepository.save(newAuthEntity);
        } else {
            if (!isValidAuth(authEntity, authRequestDto.getDevice())) {
                throw new ForbiddenException(ErrorMessage.AUTH_FORBIDDEN);
            }
        }

        String jwt = tokenProvider.createToken(authRequestDto.getDevice());

        return new AuthDto(jwt);
    }

    public AuthDto getAuth(AuthRequestDto authRequestDto, String token) {
        if (tokenProvider.validateToken(token, authRequestDto.getDevice())) {
            return new AuthDto(token);
        }

        return createAuth(authRequestDto);
    }

    private boolean isValidContract(ContractEntity contractEntity) {
        if (contractEntity != null) {
            return contractEntity.getIsActivated()
                    && contractEntity.getExpireAt().isAfter(LocalDateTime.now());
        }

        return false;
    }

    private boolean isValidAuth(AuthEntity authEntity, String device) {
        if (authEntity != null) {
            return authEntity.getIsActivated() && authEntity.getDevice().equals(device);
        }

        return false;
    }
}
