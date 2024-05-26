package com.inspocloud.usermanagementservice.service;

import com.inspocloud.usermanagementservice.model.User;

public interface UserService {
    void createNewUser(User userDetails);
    void deleteUser(String id);
    void edtUser(User user);
    User getUserDetails(String id);
}
