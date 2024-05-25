package com.cloudinspo.photomanagementservice.rabbit;

import com.cloudinspo.photomanagementservice.dto.PhotoUploadDTO;
import com.cloudinspo.photomanagementservice.model.User;


public interface RabbitMQListener {
    void receiveMessage(PhotoUploadDTO photoUploadDTO);
    void receiveEditUser(User user);
    void receiveDeleteUser(String id);
}
