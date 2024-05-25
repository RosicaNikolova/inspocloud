package com.inspocloud.authservice.model;

import lombok.Data;

@Data
public class LoginUserDTO {
    private String username;
    private String password;
}
