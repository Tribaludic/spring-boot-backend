package com.tribaludic.backend.service;

import com.tribaludic.backend.model.AuthUser;
import com.tribaludic.backend.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Andres Bustamante
 */
@Service
public class AuthUserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthUserService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    AuthUserRepository repository;

    public Optional<AuthUser> findById(UUID id) {
        return repository.findById(id);
    }

    public AuthUser create(AuthUser entity) {
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        Date currentDate = new Date();
        entity.setCreated(currentDate);
        entity.setModified(currentDate);
        entity.setLastLogin(currentDate);
        return repository.save(entity);
    }

    public AuthUser update(AuthUser entity)
    {
        entity.setModified(new Date());
        return repository.save(entity);
    }

    public boolean disable(UUID id) {

        Optional<AuthUser> optionalAuthUser = findById(id);
        if(optionalAuthUser.isPresent()){
            AuthUser authUser = optionalAuthUser.get();
            authUser.setActive(false);
            update(authUser);
            return true;
        }
        return false;
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Boolean isEmailAlreadyInUse(String email){

        if(email == null || email.isEmpty()) {
            return false;
        }

        Optional<AuthUser> optionalAuthUser = Optional.ofNullable(repository.findByEmail(email.toLowerCase()));
        if(optionalAuthUser.isPresent()){
            return  true;
        }
        return  false;
    }

    public Optional<AuthUser> loginWithEmailAndPassword(String email, String password){

        if(email==null || email.isEmpty() || password==null || password.isEmpty()){
            return Optional.empty();
        }

        Optional<AuthUser> optionalAuthUser = Optional.ofNullable(repository.findByEmail(email.toLowerCase()));
        if(optionalAuthUser.isPresent()){
            AuthUser authUser = optionalAuthUser.get();
            if(bCryptPasswordEncoder.matches(password,authUser.getPassword())){
                authUser.setLastLogin(new Date());
                authUser = update(authUser);
                optionalAuthUser = Optional.ofNullable(authUser);
                return  optionalAuthUser;
            }
        }
        return Optional.empty();
    }

}
