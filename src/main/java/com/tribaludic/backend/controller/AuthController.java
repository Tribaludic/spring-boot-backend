package com.tribaludic.backend.controller;

import com.tribaludic.backend.Utils.ControllerRequestMapping;
import com.tribaludic.backend.model.AuthUser;
import com.tribaludic.backend.response.data.*;
import com.tribaludic.backend.service.AuthUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Key;
import java.util.*;

import static com.tribaludic.backend.security.SecurityConstants.*;

/**
 * @author Andres Bustamante
 */
@RestController
@RequestMapping(ControllerRequestMapping.AUTH_CONTROLLER)
public class AuthController {

    @Autowired
    AuthUserService authUserService;

    @PostMapping("auth_hello")
    public String authHello(){
        return  "Hello from auth service";
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> signup(@RequestBody @Valid AuthUser user){

        if(authUserService.isEmailAlreadyInUse(user.getEmail())){
            Map<String,String> details = new HashMap<>();
            details.put("email", "The email is already registered");
            return new ResponseEntity<>(
                    new ExceptionResponse(details),HttpStatus.FOUND);
        }else{
            AuthUser createdUser = authUserService.create(user);
            Map<String,Object> details = new HashMap<>();
            details.put("user_id", createdUser.getId());
            details.put("user_email", createdUser.getEmail());
            details.put("user_created", createdUser.getCreated());
            details.put("user_email_verified", createdUser.getEmailVerified());
            return new ResponseEntity<>(new SuccessResponse(details),HttpStatus.CREATED);
        }

    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthUser user){

        Optional<AuthUser> optionalAuthUser = authUserService.loginWithEmailAndPassword(user.getEmail(),user.getPassword());

        if(optionalAuthUser.isPresent()){
            user = optionalAuthUser.get();
            Map<String,Object> details = new HashMap<>();
            details.put("user_id", user.getId());
            details.put("user_email", user.getEmail());
            details.put("user_created", user.getCreated());
            details.put("last_login", user.getLastLogin());
            details.put("user_email_verified", user.getEmailVerified());
            details.put("access_token", generateJwtToken(user.getEmail()));

            return new ResponseEntity<>(new SuccessResponse(details), HttpStatus.OK);
        }else{
            Map<String,String> details = new HashMap<>();
            details.put("credentials", "Invalid credentials");
            return new ResponseEntity<>(new ExceptionResponse(details), HttpStatus.NOT_FOUND);
        }
    }

    String generateJwtToken(String email){
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(KEY.getBytes());
        Claims claims = Jwts.claims().setSubject(email);
        String token = Jwts.builder().setClaims(claims).setIssuer(JWT_ISSUER).signWith(key).setExpiration(exp).compact();
        return token;
    }

}
