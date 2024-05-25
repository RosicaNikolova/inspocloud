package com.inspocloud.usermanagementservice.rabbit;

import com.inspocloud.usermanagementservice.model.User;
import com.inspocloud.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListenerRegisterUserImpl implements ListenerRegisterUser {

    private final UserService userService;
    @Override
    @RabbitListener(queues = "${user.create.queue}")
    public void receiveUser(User user) {
        System.out.println("Received user for processing: " + user.getFirstName());

        // Call user service to save the new user
        userService.createNewUser(user);
    }
}
