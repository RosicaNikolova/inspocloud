package com.inspocloud.authservice.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseLoginDTO {
    private String token;
    private String userId;
}
