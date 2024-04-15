package com.cloudinspo.photouploadservice.service;

import com.cloudinspo.photouploadservice.dto.PhotoUploadResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSenderImpl implements RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${photo.upload.exchange}")
    private String exchange;

    @Value("${photo.upload.routing-key}")
    private String routingKey;
    @Override
    public void sendPhoto(PhotoUploadResponseDTO photoDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, photoDTO);
        System.out.println("Photo sent: " + photoDTO);
    }

}
