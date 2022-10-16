package com.teamdev.auth.repository;

import com.teamdev.auth.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity, Integer>, ContractRepositoryCustom {

}
