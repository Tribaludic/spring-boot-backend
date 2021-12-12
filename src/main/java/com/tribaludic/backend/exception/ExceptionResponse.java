package com.tribaludic.backend.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Bustamante
 */
public class ExceptionResponse {

    private  Boolean error;
    private  String message;
    private Map<String, String> details;

    public ExceptionResponse(String message, Map<String, String> details) {
        this.error = true;
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
