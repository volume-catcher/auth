package com.teamdev.auth.repository;

import com.teamdev.auth.entity.AuthEntity;

import java.util.UUID;

public interface AuthRepositoryCustom {

    AuthEntity findOneAuthByDeviceAndContractIdQ(UUID device, Integer contractId);
}
