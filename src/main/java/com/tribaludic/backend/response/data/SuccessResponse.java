package com.tribaludic.backend.response.data;

import lombok.Data;

import java.util.Map;

/**
 * @author Andres Bustamante
 */
@Data
public class SuccessResponse {
    private String status = "success";
    private Map<String, Object> details;
    public SuccessResponse(Map<String, Object> details) {
        this.details = details;
    }
}
