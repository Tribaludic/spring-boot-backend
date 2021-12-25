package com.tribaludic.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBackendApplication {

    //private static Logger logger = LoggerFactory.getLogger(SpringBootBackendApplication.class);

//    public static void writeLogger(String message){
//        logger.info(message);
//    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBackendApplication.class, args);
    }

}
