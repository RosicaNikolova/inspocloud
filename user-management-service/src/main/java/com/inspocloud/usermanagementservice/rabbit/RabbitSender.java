package com.inspocloud.usermanagementservice.rabbit;

import com.inspocloud.usermanagementservice.model.User;

public interface RabbitSender {
    void sendDeleteUserToExchange(String userId);
    void sendEditUserToExchange(User user);
}
