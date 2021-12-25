package com.tribaludic.backend.repository;

import com.tribaludic.backend.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @author Andres Bustamante
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    @Query("SELECT au from AuthUser au where au.email=?1")
    AuthUser findByEmail(String email);
}
