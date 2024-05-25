package com.inspocloud.authservice.rabbit;

import com.inspocloud.authservice.model.User;

public interface RabbitMQListener {
    void receiveEditUser(User user);
    void receiveDeleteUser(String id);
}
