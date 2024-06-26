package com.cloudinspo.photomanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePhotoDTO{
    private String id;
    private String title;
    private List<String> tags;
}
