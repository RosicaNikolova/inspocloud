package com.cloudinspo.photomanagementservice.rabbit;

public interface RabbitMQSender {
    void sendPhoto(String id);
}
