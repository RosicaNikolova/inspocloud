package com.inspocloud.usermanagementservice.rabbit;

import com.inspocloud.usermanagementservice.model.User;

public interface ListenerRegisterUser {
    void receiveUser(User userRegisterDTO);
}
