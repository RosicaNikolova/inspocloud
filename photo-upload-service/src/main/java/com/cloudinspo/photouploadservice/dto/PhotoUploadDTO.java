package com.cloudinspo.photouploadservice.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUploadDTO {
    private MultipartFile file;
    private String title;
    private List<String> tags;
    private String userId;
    private String firstName;
    private String lastName;

}
