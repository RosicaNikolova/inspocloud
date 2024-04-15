package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.model.Photo;
import com.cloudinspo.photomanagementservice.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;

    @Override
    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    @Override
    public Photo getPhotoById(String id) {
        return photoRepository.findById(id).orElse(null);
    }
    @Override
    public void deletePhoto(String id) {
        photoRepository.deleteById(id);
    }
}
