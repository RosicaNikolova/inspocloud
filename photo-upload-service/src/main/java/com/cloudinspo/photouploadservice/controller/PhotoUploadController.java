package com.cloudinspo.photouploadservice.controller;

import com.cloudinspo.photouploadservice.dto.PhotoUploadDTO;
import com.cloudinspo.photouploadservice.model.Photo;
import com.cloudinspo.photouploadservice.service.PhotoUploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class PhotoUploadController {


    private final PhotoUploadService photoUploadService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test Photo Upload Service!");
    }



//
//    @PostMapping("/upload")
//    //@ResponseStatus(HttpStatus.CREATED)
//     public ResponseEntity<Object> uploadPhoto(@RequestPart("photo") MultipartFile photo,
//                                               @RequestPart("metadata") PhotoMetadataDto metadata) {
//
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);     }

//    @PostMapping("/upload")
//    public ResponseEntity<Photo> upload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
//   //     try {
//            PhotoUploadDTO photoUploadDTO = PhotoUploadDTO.builder()
//                    .title(title)
//                    .file(file)
//                    .build();
//            return photoUploadService.uploadImage(photoUploadDTO);
//   //     } catch (Exception e) {
//    //        e.printStackTrace();
//      //      return null;
//      //  }
//    }
//

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Photo> upload(@RequestPart("photoUploadDTO") String photoUploadDTOStr,
                                        @RequestPart("file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        try {
            PhotoUploadDTO photoUploadDTO = objectMapper.readValue(photoUploadDTOStr, PhotoUploadDTO.class);
            photoUploadDTO.setFile(file);
            return photoUploadService.uploadImage(photoUploadDTO);
//             }
//            catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }


}
