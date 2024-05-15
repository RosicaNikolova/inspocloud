package com.inspocloud.usermanagementservice.model;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mail;
    private String role;
}
