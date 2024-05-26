package com.inspocloud.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyCloakUser {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
