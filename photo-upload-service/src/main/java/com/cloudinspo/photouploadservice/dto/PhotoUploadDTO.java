package com.cloudinspo.photouploadservice.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Data
public class PhotoUploadDTO {
    private MultipartFile file;
    private String title;
    private List<String> tags;
}
