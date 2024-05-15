package com.batavia.ecommerce.DTOs;

import java.util.List;

import com.batavia.ecommerce.model.Role;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
    private String phoneNo;
    private String address;
}
