package com.cloudinspo.photouploadservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinspo.photouploadservice.dto.CloudinaryResponseDTO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    @Resource
    private Cloudinary cloudinary;

    @Override
    public CloudinaryResponseDTO uploadFile(MultipartFile file, String folderName) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId);
            CloudinaryResponseDTO cloudinaryResponseDTO = CloudinaryResponseDTO.builder()
                    .publicId(publicId)
                    .url(url)
                    .build();
            return cloudinaryResponseDTO;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
