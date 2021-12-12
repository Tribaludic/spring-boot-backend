package com.tribaludic.backend.service;


import com.tribaludic.backend.model.AuthUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Andres Bustamante
 */
@SpringBootTest
class AuthUserServiceTest {

    @Autowired
    AuthUserService authUserService;


    @Test()
    void create() {

        AuthUser mockUser = Mockito.mock(AuthUser.class);
        mockUser.setEmail("c.a.bustamante@live.com");
        mockUser.setPassword("6P@ssword.77");
        mockUser.setName("User Test");

        AuthUser returnedUser = authUserService.create(mockUser);
        assertNotNull(returnedUser);
//        assertEquals(mockUser.getEmail(),returnedUser.getEmail());
//        assert(Utils.bCryptPasswordEncoder().matches(mockUser.getPassword(),returnedUser.getPassword()));
//        assertEquals(mockUser.getName(),returnedUser.getName());
//        assertNotNull(returnedUser.getCreated());
//        assertNotNull(returnedUser.getAuthToken());
//        assertNotNull(returnedUser.getActive());
//        assertNotNull(returnedUser.getId());
    }

    @Test
    void update() {
    }

    @Test
    void isEmailAlreadyInUseSucess() {
        String emailInDB = "c.a.bustamante@live.com";

        //First verify the service is null safety with all cases
        assertNotNull(authUserService.isEmailAlreadyInUse(emailInDB));
        assertNotNull(authUserService.isEmailAlreadyInUse(null));
        assertNotNull(authUserService.isEmailAlreadyInUse(""));

        //Then verify the correct return boolean
        assertEquals(true,authUserService.isEmailAlreadyInUse(emailInDB));
        assertEquals(false,authUserService.isEmailAlreadyInUse(null));
        assertEquals(false,authUserService.isEmailAlreadyInUse(""));
    }

    @Test
    void isEmailAlreadyInUseFail() {

        String newEmail = "tribaludic@gmail.com";

        //First verify the service is null safety with all cases
        assertNotNull(authUserService.isEmailAlreadyInUse(newEmail));
        assertNotNull(authUserService.isEmailAlreadyInUse(null));
        assertNotNull(authUserService.isEmailAlreadyInUse(""));

        //Then verify the correct return boolean
        assertEquals(false,authUserService.isEmailAlreadyInUse(newEmail));
        assertEquals(false,authUserService.isEmailAlreadyInUse(null));
        assertEquals(false,authUserService.isEmailAlreadyInUse(""));
    }

    @Test
    void logInWithEmailAndPassword() {
    }
}
