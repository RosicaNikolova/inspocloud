package com.inspocloud.usermanagementservice.rabbit;

import com.inspocloud.usermanagementservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitSenderImpl implements RabbitSender {
    private final RabbitTemplate rabbitTemplate;

    @Value("${delete.user.exchange}")
    private String deleteUserExchange;

    @Value("${user.edit.exchange}")
    private String editUserExchange;

//    @Value("${user.create.routing-key}")
//    private String routingKey;

    @Override
    public void sendDeleteUserToExchange(String userId) {
        rabbitTemplate.convertAndSend(deleteUserExchange, "", userId);
        System.out.println("Delete user with User id: " + userId);
    }

    @Override
    public void sendEditUserToExchange(User user) {
        rabbitTemplate.convertAndSend(editUserExchange, "", user);
        System.out.println("Delete user with User id: " + user.getUserId());
    }
}
