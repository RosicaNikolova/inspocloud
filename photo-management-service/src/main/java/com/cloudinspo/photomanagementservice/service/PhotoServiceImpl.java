package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.dto.UpdatePhotoDTO;
import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.model.User;
import com.cloudinspo.photomanagementservice.rabbit.RabbitMQSender;
import com.cloudinspo.photomanagementservice.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final RabbitMQSender rabbitMQSender;

    @Override
    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    @Override
    public Photo getPhotoById(String id) {
        return photoRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePhoto(String id) {
        photoRepository.deleteById(id);
        rabbitMQSender.sendPhoto(id);
    }

    @Override
    public void updateUserDetailsPhoto(User user) {

        List<Photo> photos = photoRepository.findByUserId(user.getUserId());
        for(Photo photo: photos) {
            photo.setFirstName(user.getFirstName());
            photo.setLastName(user.getLastName());
            photoRepository.save(photo);
        }
    }

    @Override
    public void resetUserDetailsPhoto(String userId) {
        List<Photo> photos = photoRepository.findByUserId(userId);
        for(Photo photo: photos) {
            photo.setFirstName("Unknown");
            photo.setLastName("Unknown");
            photoRepository.save(photo);
        }
    }

    @Override
    public void updatePhotoDetails(UpdatePhotoDTO updatePhotoDTO) {
        Optional<Photo> optionalPhoto = photoRepository.findById(updatePhotoDTO.getId());
        if (optionalPhoto.isPresent()) {
            Photo existingPhoto = optionalPhoto.get();
            existingPhoto.setTitle(updatePhotoDTO.getTitle());
            existingPhoto.setTags(updatePhotoDTO.getTags());

            // Save edited photo
            photoRepository.save(existingPhoto);
        } else {
            throw new RuntimeException("Photo not found with id: " + updatePhotoDTO.getId());
        }
    }

}
