package com.inspocloud.usermanagementservice.controller;


import com.inspocloud.usermanagementservice.model.User;
import com.inspocloud.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        try{
//            return userService.createNewUser(user);
//        }
//        catch (HttpClientErrorException ex) {
//            return ResponseEntity.status(ex.getStatusCode())
//                    .body(ex.getResponseBodyAsString());
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editUser(@RequestBody User user) {
        userService.edtUser(user);
        return ResponseEntity.noContent().build();
    }
}
