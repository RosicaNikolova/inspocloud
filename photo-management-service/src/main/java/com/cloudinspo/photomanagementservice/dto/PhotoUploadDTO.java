package com.cloudinspo.photomanagementservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PhotoUploadDTO {

    private String uri;
    private String publicId;
    private String title;
    private List<String> tags;
    private String userId;
    private String firstName;
    private String lastName;

}
