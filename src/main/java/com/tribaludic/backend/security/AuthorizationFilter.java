package com.tribaludic.backend.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tribaludic.backend.Utils.ControllerRequestMapping;
import com.tribaludic.backend.response.data.ExceptionResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tribaludic.backend.security.SecurityConstants.*;

/**
 * @author Andres Bustamante
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        if(request.getRequestURI().contains(ControllerRequestMapping.AUTH_CONTROLLER)){
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        try {
            authenticate(request);
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.resetBuffer();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            Map<String,String> details = new HashMap<>();
            details.put("error_type", "INVALID_TOKEN");
            details.put("error_message", "Token expired, please login again");
            response.getOutputStream().print(new ObjectMapper().writeValueAsString(new ExceptionResponse(details)));
            response.flushBuffer(); // marks response as committed -- if we don't do this the request will go through normally!

        }catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            response.resetBuffer();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            Map<String,String> details = new HashMap<>();
            details.put("error_type", "TOKEN_BAD_FORMAT");
            details.put("error_message", "Bearer authorization token is not in a valid format");
            response.getOutputStream().print(new ObjectMapper().writeValueAsString(new ExceptionResponse(details)));
            response.flushBuffer(); // marks response as committed -- if we don't do this the request will go through normally!

        }
        catch (NullPointerException e) {
            response.resetBuffer();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            Map<String,String> details = new HashMap<>();
            details.put("error_type", "BEARER_AUTHORIZATION_REQUIRED");
            details.put("error_message", "Bearer authorization header is required");
            response.getOutputStream().print(new ObjectMapper().writeValueAsString(new ExceptionResponse(details)));
            response.flushBuffer(); // marks response as committed -- if we don't do this the request will go through normally!
        }
    }

    private void authenticate(HttpServletRequest request) {

        String jwtToken = request.getHeader(HEADER_NAME).replace(PREFIX, "");
        Jwt jwt = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                .requireIssuer(SecurityConstants.JWT_ISSUER)
                .build()
                .parse(jwtToken);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwt, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
