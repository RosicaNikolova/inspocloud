package com.inspocloud.authservice.rabbit;

import com.inspocloud.authservice.model.User;

public interface RabbitSender {
    void sendUser(User user);

}
