package com.teamdev.auth.repository;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamdev.auth.dto.ContractDto;
import com.teamdev.auth.entity.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.teamdev.auth.entity.QContractEntity.contractEntity;
import static com.teamdev.auth.entity.QLicenseEntity.licenseEntity;
import static com.teamdev.auth.entity.QProductEntity.productEntity;
import static com.teamdev.auth.entity.QAuthEntity.authEntity;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ContractEntity findOneContractByLicenseKeyAndProductNameQ(String licenseKey, String productName) {
        return jpaQueryFactory
                .select(contractEntity)
                .from(contractEntity)
                .join(contractEntity.license, licenseEntity)
                .on(licenseEntity.key.eq(licenseKey))
                .join(contractEntity.product, productEntity)
                .on(productEntity.name.eq(productName))
                .fetchOne();
    }

    @Override
    public ContractDto getContractInfoQ(String licenseKey, String productName) {
        return jpaQueryFactory
                .select(Projections.constructor(ContractDto.class,
                        licenseEntity.key,
                        productEntity.name,
                        contractEntity.numOfAuthAvailable,
                        Expressions.operation(Integer.class, Ops.SUB,
                                contractEntity.numOfAuthAvailable,
                                JPAExpressions
                                        .select(authEntity.id.count())
                                        .from(authEntity)
                                        .where(authEntity.contract.eq(contractEntity))),
                        contractEntity.expireAt)
                )
                .from(contractEntity)
                .join(contractEntity.license, licenseEntity)
                .on(licenseEntity.key.eq(licenseKey))
                .join(contractEntity.product, productEntity)
                .on(productEntity.name.eq(productName))
                .fetchOne();
    }
}
