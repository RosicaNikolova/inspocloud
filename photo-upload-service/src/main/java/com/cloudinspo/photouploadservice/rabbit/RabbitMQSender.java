package com.cloudinspo.photouploadservice.rabbit;

import com.cloudinspo.photouploadservice.model.Photo;

public interface RabbitMQSender {

     void sendPhoto(Photo photoDTO);
}
