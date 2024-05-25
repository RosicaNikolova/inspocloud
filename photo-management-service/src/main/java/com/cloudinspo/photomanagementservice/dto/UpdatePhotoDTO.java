package com.cloudinspo.photomanagementservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdatePhotoDTO{
    private String id;
    private String title;
    private List<String> tags;
}
