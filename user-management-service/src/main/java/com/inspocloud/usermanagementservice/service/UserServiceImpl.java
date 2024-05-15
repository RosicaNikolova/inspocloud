package com.inspocloud.usermanagementservice.service;
import com.inspocloud.usermanagementservice.model.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${keycloak.create.user.url}")
    private String createUserUrl;

    private final KeycloakService keycloakService;

    //The method should be UserDto or the token
    @Override
    public ResponseEntity<String> createNewUser(UserRegisterDTO userRegisterDTO) {

        RestTemplate restTemplate = new RestTemplate();

        //Retrieving the client token from Keycloack
        String clientToken = keycloakService.getClientToken();

        //Setting the content in the body as JSON and attaching the client token from Keycloack
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setBearerAuth(clientToken);

        // Setting up the credentials in a request body
        String requestBody = "{\"username\": \"" + userRegisterDTO.getUsername()
                + "\", \"firstName\": \"" + userRegisterDTO.getFirstName()
                + "\", \"lastName\": \"" + userRegisterDTO.getLastName()
                + "\", \"email\": \"" + userRegisterDTO.getMail() + "\", \"enabled\": true, \"credentials\": [{\"type\": \"password\", \"value\": \"" + userRegisterDTO.getPassword() + "\"}],"
                + "\"realmRoles\": [\"" + userRegisterDTO.getRole() + "\"]"
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
        System.out.println(response.getBody());

        return response;

//        if (response.getStatusCode() == HttpStatus.CREATED)
//        {
//            System.out.println(response.getBody());
//        }
//        else
//        {
//            throw new RuntimeException("Failed to create user in Keycloak: " + response.getBody());
//        }
    }

//    @Override
//    public void getUserIdByUsername(String username, String clientToken) {
//        RestTemplate restTemplate = new RestTemplate();
//        String keycloackBaseURL =  "http://localhost:8181/admin/realms/sport-spot-realm/users";
//
//        //Setting up headers of the request
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        requestHeaders.setBearerAuth(clientToken);
//
//        //Executing the request
//        ResponseEntity<String> response = restTemplate.exchange(
//                keycloackBaseURL + "/users?username=" + username,
//                HttpMethod.GET,
//                new HttpEntity<>(requestHeaders),
//                String.class
//        );
//
//        System.out.println(response);
//
//    }

}
