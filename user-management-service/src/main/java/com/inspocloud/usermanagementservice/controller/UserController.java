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

    @PostMapping("/get")
    public ResponseEntity<User> getUserDetails(@RequestBody String id) {
        System.out.println("Getting Details for user:" + id);

        User user = userService.getUserDetails(id);
        if (user != null) {
            System.out.println("Retrieved user details:" + user.getFirstName() + " " + user.getLastName());

            return ResponseEntity.ok(user);
        } else {
            System.out.println("User Not found");
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/kube")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test User Management Service!");
    }


}
