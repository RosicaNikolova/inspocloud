package com.cloudinspo.apigateway.controller;
import com.cloudinspo.apigateway.model.UserDTO;
import com.cloudinspo.apigateway.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@RequestMapping("/api/gateway")
@RestController

    public class LoginController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test API Gateway!");
    }

}


