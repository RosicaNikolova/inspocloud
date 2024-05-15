package com.cloudinspo.photomanagementservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@Data
@Builder
@Document()
public class Photo {
    @Id
    private String id;
    private String uri;
    private String title;
    private List<String> tags;
}
