package com.batavia.ecommerce.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.batavia.ecommerce.DTOs.RegistrationRequest;
import com.batavia.ecommerce.model.Role;
import com.batavia.ecommerce.model.User;
import com.batavia.ecommerce.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    @Autowired
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegistrationRequest registration) {
        List<Role> roles = Arrays.asList(Role.ROLE_ADMIN);
        var user = new User(registration.getFirstName(), registration.getLastName(), registration.getEmail(), passwordEncoder.encode(registration.getPassword()), roles, registration.getPhoneNo(), registration.getAddress());
        return userRepo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User user =  userRepo.findByEmail(email)
            .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return user;
    }

    
}