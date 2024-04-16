package com.cloudinspo.photouploadservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class PhotoUploadResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uri;
    private String title;
    private List<String> tags;
}
