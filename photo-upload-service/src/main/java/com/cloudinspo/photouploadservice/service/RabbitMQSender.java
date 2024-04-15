package com.cloudinspo.photouploadservice.service;

import com.cloudinspo.photouploadservice.dto.PhotoUploadResponseDTO;

public interface RabbitMQSender {

     void sendPhoto(PhotoUploadResponseDTO photoDTO);
}
