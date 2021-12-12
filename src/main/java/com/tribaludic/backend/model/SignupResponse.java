package com.tribaludic.backend.model;


import java.util.Date;

/**
 * @author Andres Bustamante
 */

public class SignupResponse  {
    private Boolean error;
    private String message;
    private String userId;
    private String userEmail;
    private Date userCreatedDate;
    private String accessToken;
    private Boolean isActive;

    public SignupResponse(String userId, String userEmail, Date userCreatedDate, String accessToken) {
        this.error = false;
        this.message = "User created successful";
        this.userId = userId;
        this.userEmail = userEmail;
        this.userCreatedDate = userCreatedDate;
        this.accessToken = accessToken;
        this.isActive = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Date userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }


}
