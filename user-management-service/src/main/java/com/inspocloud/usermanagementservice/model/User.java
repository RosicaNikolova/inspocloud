package com.inspocloud.usermanagementservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
}
