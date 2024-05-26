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
        Photo savedPhoto = photoRepository.save(photo);
        System.out.println("Photo details saved in Mongo" + savedPhoto);
        return savedPhoto;
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    @Override
    public Photo getPhotoById(String id) {
        System.out.println("Retrieving Photo with ID: " + id);
        return photoRepository.findByPublicId(id).orElse(null);
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
            Photo updated = photoRepository.save(photo);
            System.out.println("User Info updated for photo: " + updated.getTitle());
        }
    }

    @Override
    public void resetUserDetailsPhoto(String userId) {
        List<Photo> photos = photoRepository.findByUserId(userId);
        for(Photo photo: photos) {
            photo.setFirstName("Unknown");
            photo.setLastName("Unknown");
            Photo updated = photoRepository.save(photo);
            System.out.println("User Info deleted for photo: " + updated.getTitle());
        }
    }

    @Override
    public Photo updatePhotoDetails(UpdatePhotoDTO updatePhotoDTO) {
        System.out.println("Updating Photo with ID: " + updatePhotoDTO.getId());
        Optional<Photo> optionalPhoto = photoRepository.findByPublicId(updatePhotoDTO.getId());
        if (optionalPhoto.isPresent()) {
            Photo existingPhoto = optionalPhoto.get();
            existingPhoto.setTitle(updatePhotoDTO.getTitle());
            existingPhoto.setTags(updatePhotoDTO.getTags());
            System.out.println("Photo updated: " + optionalPhoto);

            // Save edited photo
            return photoRepository.save(existingPhoto);
        } else {
            System.out.println("Photo not found: ");
            return null;
        }
    }

}
