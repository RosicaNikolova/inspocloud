package com.inspocloud.authservice.model;


import lombok.Data;

@Data
public class RegisterUserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private String role;
}
