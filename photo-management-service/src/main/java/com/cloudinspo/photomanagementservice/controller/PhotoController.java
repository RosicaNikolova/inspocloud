package com.cloudinspo.photomanagementservice.controller;


import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
//    @PostMapping
//    public Photo addPhoto(@RequestBody Photo photo) {
//        return photoService.addPhoto(photo);
//    }

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public Photo getPhotoById(@PathVariable String id) {
        return photoService.getPhotoById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable String id) {
        photoService.deletePhoto(id);
    }
}
