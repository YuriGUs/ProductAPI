package com.example.springboot.services;

import com.example.springboot.models.UserModel;
import com.example.springboot.repositories.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email já está sendo usado");
        }

        return userRepository.save(user);
    }
}