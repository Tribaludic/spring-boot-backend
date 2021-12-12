package com.tribaludic.backend.Utils;

import com.tribaludic.backend.BackendApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Andres Bustamante
 */
public class Utils {

    private static Logger logger = LoggerFactory.getLogger(BackendApplication.class);

    static BCryptPasswordEncoder bCryptPasswordEncoder = null;

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {

        if(bCryptPasswordEncoder == null){
            bCryptPasswordEncoder = new BCryptPasswordEncoder();
        }

        return bCryptPasswordEncoder;
    }


    public static void writeLog(String log){
        logger.info(log);
    }
}
