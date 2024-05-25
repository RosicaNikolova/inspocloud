package com.cloudinspo.photouploadservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CloudinaryResponseDTO {
    private String publicId;
    private String url;
}
