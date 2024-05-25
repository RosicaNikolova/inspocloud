package com.cloudinspo.photouploadservice.service;
import com.cloudinspo.photouploadservice.dto.CloudinaryResponseDTO;
import com.cloudinspo.photouploadservice.dto.PhotoUploadDTO;
import com.cloudinspo.photouploadservice.model.Photo;
import com.cloudinspo.photouploadservice.rabbit.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PhotoUploadServiceImpl implements PhotoUploadService {

    private final CloudinaryService cloudinaryService;
    private final RabbitMQSender rabbitMQSender;

    @Override
    public ResponseEntity<Photo> uploadImage(PhotoUploadDTO photoUploadDTO) {
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
            CloudinaryResponseDTO cloudinaryResponseDTO = cloudinaryService.uploadFile(photoUploadDTO.getFile(), "folder_1");
            //photoModel.setUrl(cloudinaryService.uploadFile(photoUploadDTO.getFile(), "folder_1"));
            if(cloudinaryResponseDTO == null) {
                return ResponseEntity.badRequest().build();
            }

        Photo photo = Photo.builder()
                    .uri(cloudinaryResponseDTO.getUrl())
                    .publicId(cloudinaryResponseDTO.getPublicId())
                    .title(photoUploadDTO.getTitle())
                    .tags(photoUploadDTO.getTags())
                    .build();


        //Send metadata to Photo Management Service
        rabbitMQSender.sendPhoto(photo);

        return ResponseEntity.status(HttpStatus.CREATED).body(photo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
