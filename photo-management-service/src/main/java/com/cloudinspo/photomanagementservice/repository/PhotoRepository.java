package com.cloudinspo.photomanagementservice.repository;

import com.cloudinspo.photomanagementservice.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends MongoRepository <Photo, String> {
    List<Photo> findByUserId(String userId);
    Optional<Photo> findByPublicId(String publicId);
}
