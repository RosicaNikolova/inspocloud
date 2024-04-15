package com.cloudinspo.photouploadservice.service;

import com.cloudinspo.photouploadservice.dto.PhotoUploadDTO;
import com.cloudinspo.photouploadservice.dto.PhotoUploadResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PhotoUploadService {
    ResponseEntity<PhotoUploadResponseDTO> uploadImage(PhotoUploadDTO photoUploadDTO);

}
