package com.cloudinspo.photouploadservice.service;
import com.cloudinspo.photouploadservice.dto.PhotoUploadDTO;
import com.cloudinspo.photouploadservice.dto.PhotoUploadResponseDTO;
import com.cloudinspo.photouploadservice.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PhotoUploadServiceImpl implements PhotoUploadService {

    private final CloudinaryService cloudinaryService;
    private final RabbitMQSender rabbitMQSender;

    @Override
    public ResponseEntity<PhotoUploadResponseDTO> uploadImage(PhotoUploadDTO photoUploadDTO) {
//        try {
            if (photoUploadDTO.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (photoUploadDTO.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            //if( photoUploadDTO.getTags().isEmpty()){
             //   return ResponseEntity.badRequest().build();
           // }
            //PhotoModel photoModel = new PhotoModel();
            //photoModel.se(photoUploadDTO.getTitle());
            String returnedPhotoURL = cloudinaryService.uploadFile(photoUploadDTO.getFile(), "folder_1");
            //photoModel.setUrl(cloudinaryService.uploadFile(photoUploadDTO.getFile(), "folder_1"));
            if(returnedPhotoURL == null) {
                return ResponseEntity.badRequest().build();
            }

        Photo photoModel = Photo.builder()
                    .uri(returnedPhotoURL)
                    .title(photoUploadDTO.getTitle())
                    .tags(photoUploadDTO.getTags())
                    .build();

            //photoUploadRepository.save(photoModel);


        PhotoUploadResponseDTO photoUploadResponseDTO = PhotoUploadResponseDTO.builder()
                    //.tags(photoUploadDTO.getTags())
                    .title(photoModel.getTitle())
                    .uri(photoModel.getUri())
                    .tags(photoModel.getTags())
                    .build();

        //Send metadata to Photo Management Service
        rabbitMQSender.sendPhoto(photoUploadResponseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(photoUploadResponseDTO);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
