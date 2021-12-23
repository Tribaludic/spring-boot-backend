package com.tribaludic.backend.model;

import com.tribaludic.backend.Utils.Utils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;


    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message = "the email is not in a valid format")
    @NotBlank(message = "the email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "the password cannot be empty")
    @Pattern(regexp="((?=.*[0-9].{1})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&*()+=._-]).{6,60})",message="The password must have more than 6 characters and must have at least one uppercase, one lowercase, two numbers, and one of the following characters: @,#,$,%,&,*,(,),+,=,.,_,-")
    @Column(nullable = false, length = 60)
    private String password;

    @NotBlank(message = "the name cannot be empty")
    @Column(nullable = false)
    @Pattern(regexp="^[A-Za-zàèìòù ]{5,35}$",message="The name must have more than 6 and less than 35 characters and cannot contain numbers or special characters")
    private String name;

    private Date created;
    private Date modified;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "auth_token",length = 512)
    private String authToken;


    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @PrePersist
    @PreUpdate
    private void prepareData(){
        if(email != null && !email.isEmpty()){
            email = email.toLowerCase();
        }

        if(password != null && !password.isEmpty()){
            password = Utils.bCryptPasswordEncoder().encode(password);
        }

    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String accessToken) {
        this.authToken = accessToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}