package com.cloudinspo.photomanagementservice.service;

import com.cloudinspo.photomanagementservice.dto.PhotoUploadDTO;

public interface PhotoRecieveService {
    public void receiveMessage(PhotoUploadDTO photoUploadDTO);
}
