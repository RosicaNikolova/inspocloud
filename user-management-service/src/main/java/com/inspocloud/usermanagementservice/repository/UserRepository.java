package com.inspocloud.usermanagementservice.repository;

import com.inspocloud.usermanagementservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
