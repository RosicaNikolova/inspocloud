package com.cloudinspo.photouploadservice.model;



import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

//@Entity
@Data
@Builder
public class Photo {
    private String uri;
    private String publicId;
    private String title;
    private List<String> tags;
    private String userId;
    private String firstName;
    private String lastName;
}
