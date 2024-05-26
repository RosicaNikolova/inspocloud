package com.cloudinspo.photomanagementservice.rabbit;

import com.cloudinspo.photomanagementservice.dto.PhotoUploadDTO;
import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.model.User;
import com.cloudinspo.photomanagementservice.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQListenerImpl implements RabbitMQListener {

    private final PhotoService photoService;

    @Override
    @RabbitListener(queues = "${photo.upload.queue}")
    public void receiveMessage(PhotoUploadDTO photoUploadDTO) {
        // Assuming PhotoUploadDTO is the DTO containing the data you sent from the Photo Upload Service
        System.out.println("Received photo for processing from Photo Upload Service: " + photoUploadDTO.getTitle());
        Photo photo = Photo.builder()
                .uri(photoUploadDTO.getUri())
                .publicId(photoUploadDTO.getPublicId())
                .title(photoUploadDTO.getTitle())
                .tags(photoUploadDTO.getTags())
                .userId(photoUploadDTO.getUserId())
                .firstName(photoUploadDTO.getFirstName())
                .lastName(photoUploadDTO.getLastName())
                .build();

        // Save photo details to a database.
        photoService.addPhoto(photo);
    }

    @Override
    @RabbitListener(queues = "${edit.queue.ps}")
    public void receiveEditUser(User user) {
        System.out.println("Received user to edit" + user);
        photoService.updateUserDetailsPhoto(user);
    }

    @Override
    @RabbitListener(queues = "${delete.queue.ps}")
    public void receiveDeleteUser(String id) {
        photoService.resetUserDetailsPhoto(id);
    }
}
