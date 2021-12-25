package com.tribaludic.backend.security;

/**
 * @author Andres Bustamante
 */
public class SecurityConstants {
    public static final String JWT_ISSUER = "tribaludic";
    public static final String KEY = "fUjWnZr4u7x!A%D*G-KaPdSgVkYp2s5v8y/B?E(H+MbQeThWmZq4t6w9z$C&F)J@";
    public static final String HEADER_NAME = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final Long EXPIRATION_TIME = 1000L*60*1;//1 minuto
}
