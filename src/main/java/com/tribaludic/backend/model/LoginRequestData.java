package com.tribaludic.backend.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Andres Bustamante
 */
@Data
public class LoginRequestData {

    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message = "the email is not in a valid format")
    @NotBlank(message = "the email cannot be empty")
    private String email;

    @NotBlank(message = "the password cannot be empty")
    @Pattern(regexp="((?=.*[0-9].{1})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*()+=._-]).{6,60})",message="The password must have more than 6 characters and must have at least one uppercase, one lowercase, two numbers, and one of the following characters: @,#,$,%,&,*,(,),+,=,.,_,-")
    private String password;

}
