package com.cloudinspo.photomanagementservice.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSenderImpl implements RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${delete.photo.exchange}")
    private String exchange;

    @Value("${delete.photo.routing-key}")
    private String routingKey;

    @Override
    public void sendPhoto(String id) {
        rabbitTemplate.convertAndSend(exchange, routingKey, id);
        System.out.println("User sent: " + id);
    }
}
