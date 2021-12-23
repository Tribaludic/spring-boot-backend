package com.tribaludic.backend.controller;

import com.tribaludic.backend.exception.ExceptionResponse;
import com.tribaludic.backend.model.AuthUser;
import com.tribaludic.backend.model.LoginRequestData;
import com.tribaludic.backend.model.SignupResponse;
import com.tribaludic.backend.model.TestModel;
import com.tribaludic.backend.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;

/**
 * @author Andres Bustamante
 */
@RestController
@RequestMapping("/tribaludic/v1/")
public class AuthController{

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthUserService authUserService;

    @GetMapping("test")
    ResponseEntity<TestModel> test( @RequestBody AuthUser user){

        return new ResponseEntity<>(new TestModel("hola from server"), HttpStatus.OK);
    }

    @PostMapping(value = "auth/signup", produces = {"application/json"})
    ResponseEntity<?> signup(@Valid @RequestBody AuthUser user ){

        //Utils.writeLog("original password = "+user.getPassword());
        //Utils.writeLog("encrypted password = "+ Utils.bCryptPasswordEncoder().encode(user.getPassword()));

        if(authUserService.isEmailAlreadyInUse(user.getEmail())){
            Map<String,String> details = new HashMap<>();
            details.put("email", user.getEmail().toLowerCase());
            return new ResponseEntity<>(new ExceptionResponse("Email already registered",details),HttpStatus.FOUND);
        }
        AuthUser createdUser = authUserService.create(user);

        return new ResponseEntity<>(new SignupResponse(createdUser.getId().toString(), createdUser.getEmail(), createdUser.getCreated(), createdUser.getAuthToken()) ,HttpStatus.CREATED);
    }

    @PostMapping(value = "auth/login", produces = {"application/json"})
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestData user ){

        Optional<AuthUser> authUser = authUserService.logInWithEmailAndPassword(user.getEmail(),user.getPassword());


        logger.info("AN USER IS TRYING TTO LOGIN");
        logger.info(user.getEmail());

        if(authUser.isPresent()){
            logger.info("LOGIN SUCCESSFUL");
            return new ResponseEntity<>(authUser,HttpStatus.OK);
        }
        Map<String,String> details = new HashMap<>();
        details.put("errorType", "there is no user with these credentials");
        logger.info("LOGIN FAILED");
        return new ResponseEntity<>(new ExceptionResponse("Log in failed",details),HttpStatus.BAD_REQUEST);
    }

}
