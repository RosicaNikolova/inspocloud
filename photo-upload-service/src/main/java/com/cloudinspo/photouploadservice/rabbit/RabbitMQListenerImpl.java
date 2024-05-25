package com.cloudinspo.photouploadservice.rabbit;

import com.cloudinspo.photouploadservice.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQListenerImpl implements RabbitMQListener {
    private final CloudinaryService cloudinaryService;

    @Override
    @RabbitListener(queues = "${delete.photo.queue}")
    public void receiveDeletePhoto(String id) {
        cloudinaryService.deleteFile(id);
    }
}
