package com.cloudinspo.photouploadservice.rabbit;

public interface RabbitMQListener {
    void receiveDeletePhoto(String id);
}
