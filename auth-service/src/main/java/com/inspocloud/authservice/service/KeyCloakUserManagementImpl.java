package com.inspocloud.authservice.service;

import com.inspocloud.authservice.keycloak.KeycloakService;
import com.inspocloud.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KeyCloakUserManagementImpl implements KeyCloakUserManagement {

    private final KeycloakService keycloakService;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;


    @Override
    public void editUser(User user) {
        final String url = authServerUrl + "admin/realms/" + realm + "/users/" + user.getUserId();

        String clientToken = keycloakService.getClientToken();

        //Setting up the headers of the HTTP request. To be more specific the type of the content in the body is set up
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(clientToken);

        // create request
        HttpEntity<User> request = new HttpEntity<>(user, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                User.class);
    }

    @Override
    public void deleteUser(String id) {
        final String url = authServerUrl + "admin/realms/" + realm + "/users/" + id;
        String clientToken = keycloakService.getClientToken();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(clientToken);

        HttpEntity<User> request = new HttpEntity<>(requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                request,
                Void.class);
    }
}
