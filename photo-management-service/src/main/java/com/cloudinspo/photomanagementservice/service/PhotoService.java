package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.dto.UpdatePhotoDTO;
import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.model.User;

import java.util.List;

public interface PhotoService {

    Photo addPhoto(Photo photo);

    List<Photo> getAllPhotos();

    Photo getPhotoById(String id);

    void deletePhoto(String id);

    void updateUserDetailsPhoto(User user);

    void resetUserDetailsPhoto(String userId);

    Photo updatePhotoDetails(UpdatePhotoDTO updatePhotoDTO);
}
