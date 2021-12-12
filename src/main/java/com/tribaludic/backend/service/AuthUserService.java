package com.tribaludic.backend.service;

import com.tribaludic.backend.Utils.Utils;
import com.tribaludic.backend.exception.ExceptionResponse;
import com.tribaludic.backend.model.AuthUser;
import com.tribaludic.backend.repository.AuthUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Andres Bustamante
 */
@Service
public class AuthUserService implements ICrudService<AuthUser>{

    @Autowired
    AuthUserRepository repository;

    @Autowired
    Environment env;

    @Override
    public List<AuthUser> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AuthUser> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public AuthUser create(AuthUser entity) {
        Date currentDate = new Date();
        entity.setCreated(currentDate);
        entity.setModified(currentDate);
        entity.setLastLogin(currentDate);
        entity.setAuthToken(getBearerToken(entity.getEmail().toLowerCase()));
        entity.setActive(true);
        return repository.save(entity);
    }

    @Override
    public AuthUser update(AuthUser entity)
    {
        entity.setModified(new Date());
        return repository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Boolean isEmailAlreadyInUse(String email){

        if(email == null || email.isEmpty()) {

            return false;
        }

        Optional<AuthUser> optionalAuthUser = Optional.ofNullable(repository.findAuthUserByEmail(email.toLowerCase()));
        if(optionalAuthUser.isPresent()){
            return  true;
        }
        return  false;
    }

    public Optional<AuthUser> logInWithEmailAndPassword(String email,String password){

        if(email==null || email.isEmpty() || password==null || password.isEmpty()){
            return Optional.empty();
        }

        Optional<AuthUser> optionalAuthUser = Optional.ofNullable(repository.findAuthUserByEmail(email.toLowerCase()));
        if(optionalAuthUser.isPresent()){
            AuthUser authUser = optionalAuthUser.get();
            Boolean matchPasswords = Utils.bCryptPasswordEncoder().matches(password,authUser.getPassword());

            if(matchPasswords){
                authUser.setLastLogin(new Date());
                authUser.setAuthToken(getBearerToken(authUser.getEmail()));
                update(authUser);
            }

        }
        return  optionalAuthUser;
    }

    private String getBearerToken(String subject) {

        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Long tokenExpiration = 600000L;

        Utils.writeLog("MILISECONDS--------------------------------");
        Date d1 = new Date(System.currentTimeMillis());
        Date d2 = new Date(System.currentTimeMillis()+tokenExpiration);
        Utils.writeLog(d1.toString());
        Utils.writeLog(d2.toString());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(subject)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration) )
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        Utils.writeLog("Bearer " + token);

        //return "Bearer " + token;
        return token;
    }




}
