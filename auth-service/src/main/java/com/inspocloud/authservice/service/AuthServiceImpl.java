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


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<String> createNewUser(RegisterUserDTO registerUserDTO) {

        registerUserDTO.setRole("user");
        RestTemplate restTemplate = new RestTemplate();

        //Retrieving the client token from Keycloack
        String clientToken = keycloakService.getClientToken();

        //Setting the content in the body as JSON and attaching the client token from Keycloack
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(clientToken);


        //  Setting up the credentials in a request body
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("username", registerUserDTO.getUsername());
        requestMap.put("firstName", registerUserDTO.getFirstName());
        requestMap.put("lastName", registerUserDTO.getLastName());
        requestMap.put("email", registerUserDTO.getMail());
        requestMap.put("enabled", true);
        requestMap.put("credentials", Collections.singletonList(Collections.singletonMap("type", "password")));
        requestMap.put("realmRoles", Collections.singletonList(registerUserDTO.getRole()));

        //Attach headers and body to request
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestMap, requestHeaders);

//        // Setting up the credentials in a request body
//        String requestBody = "{\"username\": \"" + registerUserDTO.getUsername()
//                + "\", \"firstName\": \"" + registerUserDTO.getFirstName()
//                + "\", \"lastName\": \"" + registerUserDTO.getLastName()
//                + "\", \"email\": \"" + registerUserDTO.getMail() + "\", \"enabled\": true, \"credentials\": [{\"type\": \"password\", \"value\": \"" + registerUserDTO.getPassword() + "\"}],"
//                + "\"realmRoles\": [\"" + registerUserDTO.getRole() + "\"]"
//                +"}";
//
//        //Attaching the headers and the body to the request to Keycloack
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, requestHeaders);

        //Executing the request
        ResponseEntity<String> response =  restTemplate.exchange(
                createUserUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.CREATED || statusCode == HttpStatus.OK) {
            System.out.println("User was created successfully.");
            String locationUrl = response.getHeaders().getFirst("Location");
            if (locationUrl != null) {
                String userId = locationUrl.substring(locationUrl.lastIndexOf('/') + 1);
                User user = User.builder()
                        .userId(userId)
                        .firstName(registerUserDTO.getFirstName())
                        .lastName(registerUserDTO.getLastName())
                        .build();
                rabbitMQSender.sendUser(user);
            }
        }
        return response;
    }

    @Override
    public ResponseLoginDTO login(LoginUserDTO user) {

        final String userTokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
//        System.out.println(userTokenUrl);
//        System.out.println(clientId);
//        System.out.println(clientSecret);

        RestTemplate restTemplate = new RestTemplate();
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());

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
        Jwt<?, ?> jwtToken = Jwts.parserBuilder().build().parse(accessToken);

        Claims claims = (Claims) jwtToken.getBody();

        String userId = claims.get("sub", String.class);

        return ResponseLoginDTO.builder()
                        .token(accessToken)
                        .userId(userId)
                        .build();

    }

}
