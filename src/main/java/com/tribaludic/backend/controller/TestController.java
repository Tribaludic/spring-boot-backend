package com.tribaludic.backend.controller;

import com.tribaludic.backend.Utils.ControllerRequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andres Bustamante
 */

@RestController
@RequestMapping(ControllerRequestMapping.TEST_CONTROLLER)
public class TestController {

    @RequestMapping("test")
    public String test(){
        return  "private information";
    }

}
