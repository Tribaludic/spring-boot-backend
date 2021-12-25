package com.tribaludic.backend.response.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Andres Bustamante
 */
@Data
public class ExceptionResponse {

    private String status = "error";
    private Map<String, String> details;

    public ExceptionResponse(Map<String, String> details) {
        this.details = details;
    }



}
