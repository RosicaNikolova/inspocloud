package com.inspocloud.authservice.service;

import com.inspocloud.authservice.model.User;

public interface KeyCloakUserManagement {
    void editUser(User user);
    void deleteUser(String id);
}
