package com.cloudinspo.photouploadservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoMetadataDto {
    private String title;
    private List<String> tags;
}

