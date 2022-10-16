package com.teamdev.auth.repository;


import com.teamdev.auth.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, Integer>, AuthRepositoryCustom {

}
