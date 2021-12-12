package com.tribaludic.backend.repository;

import com.tribaludic.backend.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {
    @Query("SELECT au from AuthUser au where au.email=?1 and  au.password=?2")
    AuthUser findAuthUserByEmailAndPassword(String email, String password);

    @Query("SELECT au from AuthUser au where au.email=?1")
    AuthUser findAuthUserByEmail(String email);
}