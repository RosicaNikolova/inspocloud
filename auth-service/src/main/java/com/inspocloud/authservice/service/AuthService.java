package com.inspocloud.authservice.service;

import com.inspocloud.authservice.model.LoginUserDTO;
import com.inspocloud.authservice.model.RegisterUserDTO;
import com.inspocloud.authservice.model.ResponseLoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> createNewUser(RegisterUserDTO user);
    ResponseLoginDTO login(LoginUserDTO user);

}
