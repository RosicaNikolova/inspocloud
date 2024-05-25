package com.cloudinspo.photomanagementservice.controller;

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

    @GetMapping("/photos")
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable String id) {
        Photo photo = photoService.getPhotoById(id);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(photo);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/edit")
    public ResponseEntity<Void> updatePhoto(@RequestBody UpdatePhotoDTO updatePhotoDTO) {
        photoService.updatePhotoDetails(updatePhotoDTO);
        return ResponseEntity.noContent().build();
    }
}
