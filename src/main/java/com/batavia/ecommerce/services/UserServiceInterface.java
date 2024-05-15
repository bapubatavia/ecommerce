package com.batavia.ecommerce.services;


import com.batavia.ecommerce.DTOs.RegistrationRequest;
import com.batavia.ecommerce.model.User;

public interface UserServiceInterface {

    User registerUser(RegistrationRequest registrationRequest);

    User findByEmail(String email);
}
