package com.tribaludic.backend.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

/**
 * @author Andres Bustamante
 */
@Data
@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" ,message = "the email is not in a valid format")
    @NotBlank(message = "The email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "The password cannot be empty")
    @Pattern(regexp="((?=.*\\d.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*()+=._-]).{6,})",message="The password must have a minimum of 6 characters and must have at least one uppercase, one lowercase, two numbers and one of the following signs: @, #, $,%, &, *, (,), +, = ,. , _, -")
    @Column(nullable = false)
    private String password;

    private Date created;
    private Date modified;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;
}
