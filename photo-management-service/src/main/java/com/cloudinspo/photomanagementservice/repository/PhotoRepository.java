package com.cloudinspo.photomanagementservice.repository;

import com.cloudinspo.photomanagementservice.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends MongoRepository <Photo, String> {
    List<Photo> findByUserId(String userId);
}
