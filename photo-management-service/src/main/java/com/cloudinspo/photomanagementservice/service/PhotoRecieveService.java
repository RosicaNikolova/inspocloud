package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.dto.PhotoUploadDTO;

public interface PhotoRecieveService {
    void receiveMessage(PhotoUploadDTO photoUploadDTO);
}
