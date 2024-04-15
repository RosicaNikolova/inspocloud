package com.cloudinspo.photouploadservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class PhotoUploadResponseDTO {
    private String uri;
    private String title;
    private List<String> tags;
}
