package com.cloudinspo.photouploadservice.controller;

import com.cloudinspo.photouploadservice.dto.PhotoUploadDTO;
import com.cloudinspo.photouploadservice.dto.PhotoUploadResponseDTO;
import com.cloudinspo.photouploadservice.service.PhotoUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class PhotoUploadController {


    private final PhotoUploadService photoUploadService;

    @GetMapping("/test")
    public String TestPhotoUpload() {
        return "Test successful";
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

    @PostMapping("/upload")
    public ResponseEntity<PhotoUploadResponseDTO> upload(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
   //     try {
            PhotoUploadDTO photoUploadDTO = PhotoUploadDTO.builder()
                    .title(title)
                    .file(file)
                    .build();
            return photoUploadService.uploadImage(photoUploadDTO);
   //     } catch (Exception e) {
    //        e.printStackTrace();
      //      return null;
      //  }
    }


}
