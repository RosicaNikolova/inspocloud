package com.cloudinspo.apigateway.service;
import com.cloudinspo.apigateway.model.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//
//import java.util.Map;
//import java.util.Objects;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    @Value("${keycloak.resource}")
//    private String clientId;
//
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;
//
//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//
//    @Override
//    public String login(UserDTO user) {
//
//        final String userTokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
////        System.out.println(userTokenUrl);
////        System.out.println(clientId);
////        System.out.println(clientSecret);
//
//        RestTemplate restTemplate = new RestTemplate();
////        System.out.println(user.getUsername());
////        System.out.println(user.getPassword());
//
//        //Setting up the headers of the HTTP request
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        //Sett up the body of the request.
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("grant_type", "password");
//        requestBody.add("client_id", clientId);
//        requestBody.add("client_secret", clientSecret);
//        requestBody.add("username", user.getUsername());
//        requestBody.add("password", user.getPassword());
//
//        //Configuring and executing the request.
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
//
//        ResponseEntity<Map> response = restTemplate.exchange(
//                userTokenUrl,
//                HttpMethod.POST,
//                requestEntity,
//                Map.class
//        );
//        System.out.println(response.getBody());
//        String accessToken = (String) Objects.requireNonNull(response.getBody()).get("access_token");
//        return accessToken;
//    }
//}
