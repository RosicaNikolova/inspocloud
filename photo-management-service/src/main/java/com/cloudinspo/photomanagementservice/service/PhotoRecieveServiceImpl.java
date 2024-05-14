package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.dto.PhotoUploadDTO;
import com.cloudinspo.photomanagementservice.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoRecieveServiceImpl implements PhotoRecieveService{

    private final PhotoService photoService;
    @Override
    @RabbitListener(queues = "${photo.management.queue}")
    public void receiveMessage(PhotoUploadDTO photoUploadDTO) {
        // Assuming PhotoUploadDTO is the DTO containing the data you sent from the Photo Upload Service
        System.out.println("Received photo for processing: " + photoUploadDTO.getTitle());
        Photo photo = Photo.builder()
                .uri(photoUploadDTO.getUri())
                .title(photoUploadDTO.getTitle())
                .tags(photoUploadDTO.getTags())
                .build();

        photoService.addPhoto(photo);
        // Here, implement the logic to process the message.
        // For example, save photo details to a database.
    }
}
