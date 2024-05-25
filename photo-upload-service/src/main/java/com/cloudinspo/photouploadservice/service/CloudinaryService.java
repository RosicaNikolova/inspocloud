package com.cloudinspo.photouploadservice.service;

import com.cloudinspo.photouploadservice.dto.CloudinaryResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    CloudinaryResponseDTO uploadFile(MultipartFile file, String folderName);

    void deleteFile(String publicId);
}