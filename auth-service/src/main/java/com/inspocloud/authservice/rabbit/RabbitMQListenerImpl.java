package com.inspocloud.authservice.rabbit;

import com.inspocloud.authservice.model.User;
import com.inspocloud.authservice.service.KeyCloakUserManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
@RequiredArgsConstructor
public class RabbitMQListenerImpl implements RabbitMQListener {
    private final KeyCloakUserManagement keyCloakUserManagement;

    @Override
    @RabbitListener(queues = "${edit.queue}")
    public void receiveEditUser(User user) {
        System.out.println("Received user for editing: " + user.getFirstName());

        // Call user service to save the new user
        keyCloakUserManagement.editUser(user);
    }

    @Override
    @RabbitListener(queues = "${delete.queue}")
    public void receiveDeleteUser(String id) {
        System.out.println("Received user for deleting: " + id);

        // Call user service to save the new user
        keyCloakUserManagement.deleteUser(id);

    }
}
