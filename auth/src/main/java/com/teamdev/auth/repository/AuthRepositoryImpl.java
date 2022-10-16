package com.teamdev.auth.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamdev.auth.entity.AuthEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.teamdev.auth.entity.QAuthEntity.authEntity;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AuthEntity findOneAuthByDeviceAndContractIdQ(UUID device, Integer contractId) {
        return jpaQueryFactory
                .select(authEntity)
                .from(authEntity)
                .where(authEntity.device.eq(device))
                .where(authEntity.contract.id.eq(contractId))
                .fetchOne();
    }
}
