package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.model.Photo;

import java.util.List;

public interface PhotoService {

    Photo addPhoto(Photo photo);
    List<Photo> getAllPhotos();

    Photo getPhotoById(String id);

    void deletePhoto(String id);
}
