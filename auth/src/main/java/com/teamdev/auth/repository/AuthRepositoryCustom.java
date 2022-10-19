package com.teamdev.auth.repository;

import com.teamdev.auth.entity.AuthEntity;

public interface AuthRepositoryCustom {

    AuthEntity findOneAuthByDeviceAndContractIdQ(String device, Integer contractId);
    Long findNumOfAuthByContractIdQ(Integer contractId);
}
