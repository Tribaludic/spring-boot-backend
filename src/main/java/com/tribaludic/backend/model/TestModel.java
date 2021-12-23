package com.tribaludic.backend.model;

import lombok.Data;

/**
 * @author Andres Bustamante
 */
@Data
public class TestModel {

    private String message;

    public TestModel(String message) {
        this.message = message;
    }

}
