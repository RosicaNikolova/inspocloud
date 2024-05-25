package com.cloudinspo.apigateway.controller;
import com.cloudinspo.apigateway.model.UserDTO;
import com.cloudinspo.apigateway.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

//@RequiredArgsConstructor
//    @RestController
//    public class LoginController {
//
//    //private final LoginService loginService;
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserDTO user) {
//        try {
//            //String response = loginService.login(user);
//            return ResponseEntity.ok(response);
//        }
//        catch (HttpClientErrorException ex) {
//            return ResponseEntity.status(ex.getStatusCode())
//                    .body(ex.getResponseBodyAsString());
//        }
//    }

//}


