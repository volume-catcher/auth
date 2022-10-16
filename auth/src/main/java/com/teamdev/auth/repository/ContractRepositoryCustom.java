package com.teamdev.auth.repository;

import com.teamdev.auth.dto.ContractDto;
import com.teamdev.auth.entity.ContractEntity;

public interface ContractRepositoryCustom {

    ContractEntity findOneContractByLicenseKeyAndProductNameQ(String licenseKey, String productName);
    ContractDto getContractInfoQ(String licenseKey, String productName);
}
