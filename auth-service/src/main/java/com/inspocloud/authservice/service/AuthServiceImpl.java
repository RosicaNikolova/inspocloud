package com.inspocloud.authservice.service;

import com.inspocloud.authservice.keycloak.KeycloakService;
import com.inspocloud.authservice.model.LoginUserDTO;
import com.inspocloud.authservice.model.RegisterUserDTO;
import com.inspocloud.authservice.model.ResponseLoginDTO;
import com.inspocloud.authservice.model.User;
import com.inspocloud.authservice.rabbit.RabbitSender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.create.user.url}")
    private String createUserUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private final KeycloakService keycloakService;

    private final RabbitSender rabbitMQSender;


    @Override
    public String createNewUser(RegisterUserDTO registerUserDTO) {

        registerUserDTO.setRole("user");
        RestTemplate restTemplate = new RestTemplate();

        //Retrieving the client token from Keycloack
        String clientToken = keycloakService.getClientToken();

        if (clientToken != null && !clientToken.isEmpty()) {
            System.out.println("Authentication service authenticated to keycloak, client token received");
        }

        //Setting the content in the body as JSON and attaching the client token from Keycloack
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(clientToken);


        // Setting up the credentials in a request body
        String requestBody = "{\"username\": \"" + registerUserDTO.getUsername()
                + "\", \"firstName\": \"" + registerUserDTO.getFirstName()
                + "\", \"lastName\": \"" + registerUserDTO.getLastName()
                + "\", \"email\": \"" + registerUserDTO.getMail()
                + "\", \"enabled\": true, \"credentials\": [{\"type\": \"password\", \"value\": \"" + registerUserDTO.getPassword() + "\"}],"
                + "\"realmRoles\": [\"" + registerUserDTO.getRole() + "\"]"
                +"}";

        //Attaching the headers and the body to the request to Keycloack
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        //Executing the request
        ResponseEntity<String> response =  restTemplate.exchange(
                createUserUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String userId= null;
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.CREATED || statusCode == HttpStatus.OK) {
            String locationUrl = response.getHeaders().getFirst("Location");
            if (locationUrl != null) {
                userId = locationUrl.substring(locationUrl.lastIndexOf('/') + 1);

                System.out.println("User was created successfully with id" + userId);
                User user = User.builder()
                        .userId(userId)
                        .firstName(registerUserDTO.getFirstName())
                        .lastName(registerUserDTO.getLastName())
                        .email(registerUserDTO.getMail())
                        .build();
                rabbitMQSender.sendUser(user);
            }

        }
        return userId;
    }

    @Override
    public ResponseLoginDTO login(LoginUserDTO user) {
        System.out.println("Received user for login: " + user);

        final String userTokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        //Setting up the headers of the HTTP request
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //Sett up the body of the request.
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", user.getUsername());
        requestBody.add("password", user.getPassword());

        //Configuring and executing the request.
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        ResponseEntity<Map> response = restTemplate.exchange(
                userTokenUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );
        System.out.println(response.getBody());
        String accessToken = (String) Objects.requireNonNull(response.getBody()).get("access_token");
        // Parse the JWT token
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2byXWUbcQcMSxZ3AVM/C8LAPDvYX70IISWY+79cQhwamy22SEmm8tsBToqK5W00tk3HwZvPgRqR3rlJq6GcW/nMDZsxdIMD6yltRC5BcT49+AYZ75Op7Cb9ngb/OfsQw17fpdojtEV7XSnUi5btLl4KNXZoSeNHkzKIRnWF836dOcP6EW9y5RDR/PJcRuB1CjmB+Xe4piGTKidyio4zGlsP2h9xoU+gXeXmKvthP0EtyKmZW6/37VIUFG1ya5O8+AoEZkz/WwybzDskFYcPr2oe4y7IZLKqGAhL7o0ade9cRebNTPOw1M35UrW7hJN4lcoYaxGxz+R2u/11RUgchdQIDAQAB";
        PublicKey publicKey = getPublicKey(key);
        Jwt<?,?> jwtToken = Jwts.parserBuilder().setSigningKey(publicKey).build().parse(accessToken);

        Claims claims = (Claims) jwtToken.getBody();

        String userId = claims.get("sub", String.class);
        System.out.println("User logged in successfully with id: " + userId);
        return ResponseLoginDTO.builder()
                        .token(accessToken)
                        .userId(userId)
                        .build();

    }

    public PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

}
