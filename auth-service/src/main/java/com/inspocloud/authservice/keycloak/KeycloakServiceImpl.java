package com.inspocloud.authservice.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.username}")
    private String keycloakUser;

    @Value("${keycloak.password}")
    private String keycloakPassword;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;


    @Override
    public String getClientToken()
    {
        final String clientTokenURL = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        //Setting up the headers of the HTTP request. To be more specific the type of the content in the body is set up
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //Setting up the body of the HTTP request
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", keycloakUser);
        requestBody.add("password", keycloakPassword);

        System.out.println(clientTokenURL);

        //Adding the headers and the body to the HTTP request
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        //Executing the HTTP request
        ResponseEntity<Map> response = restTemplate.exchange(
                clientTokenURL,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        String clientToken = (String) response.getBody().get("access_token");
        System.out.println(clientToken);
        return clientToken;
    }
}
