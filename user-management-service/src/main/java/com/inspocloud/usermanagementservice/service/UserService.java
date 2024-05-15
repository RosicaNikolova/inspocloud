package com.inspocloud.usermanagementservice.service;

import com.inspocloud.usermanagementservice.model.UserRegisterDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<String> createNewUser(UserRegisterDTO userDetails);
}
