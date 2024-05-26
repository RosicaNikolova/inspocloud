package com.inspocloud.authservice.rabbit;

import com.inspocloud.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitSenderImpl implements RabbitSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${user.create.exchange}")
    private String exchange;

    @Value("${user.create.routing-key}")
    private String routingKey;
    @Override
    public void sendUser(User user) {
        rabbitTemplate.convertAndSend(exchange, routingKey, user);
        System.out.println("User sent to User Management Service: " + user);
    }
}
