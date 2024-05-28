package com.inspocloud.authservice.controller;

import com.inspocloud.authservice.model.LoginUserDTO;
import com.inspocloud.authservice.model.RegisterUserDTO;
import com.inspocloud.authservice.model.ResponseLoginDTO;
import com.inspocloud.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    //Testing pipeline 3

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test Auth Service!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        try{
            String userId = authService.createNewUser(registerUserDTO);
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        }
        catch (RestClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUserDTO) {
        try {
            ResponseLoginDTO responseLoginDTO = authService.login(loginUserDTO);
            return ResponseEntity.ok(responseLoginDTO);
        }
        catch (RestClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getMessage());
        }
    }
}
