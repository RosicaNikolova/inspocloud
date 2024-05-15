package com.cloudinspo.apigateway.service;

import com.cloudinspo.apigateway.model.UserDTO;

public interface LoginService {

    String login(UserDTO user);
}
