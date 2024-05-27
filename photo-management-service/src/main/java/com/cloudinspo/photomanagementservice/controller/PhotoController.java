package com.cloudinspo.photomanagementservice.controller;

import com.cloudinspo.photomanagementservice.dto.PhotoRequest;
import com.cloudinspo.photomanagementservice.dto.UpdatePhotoDTO;
import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    //Testing pipeline 7

    @GetMapping("/photos")
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @PostMapping("/get")
    public ResponseEntity<Photo> getPhotoById(@RequestBody PhotoRequest photoRequest) {
        Photo photo = photoService.getPhotoById(photoRequest.getPublicId());
        if (photo == null) {
            System.out.println("Photo not found");
            return ResponseEntity.notFound().build();
        }
        System.out.println("Photo found: " + photo);
        return ResponseEntity.ok(photo);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/edit")
    public ResponseEntity<Photo> updatePhoto(@RequestBody UpdatePhotoDTO updatePhotoDTO) {
        Photo photo = photoService.updatePhotoDetails(updatePhotoDTO);
        if (photo == null) {
            System.out.println("Photo not found");
            return ResponseEntity.notFound().build();
        }
        System.out.println("Photo updated " + photo);
        return ResponseEntity.ok(photo);
    }
}
