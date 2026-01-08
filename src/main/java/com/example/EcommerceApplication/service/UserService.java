package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.UserNotFoundException;
import com.example.EcommerceApplication.repository.UserRepository;
import com.example.EcommerceApplication.responsce.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getById(long id){
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(UserResponse.USER_NOT_FOUND.name()));
    }
    public User getByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(UserResponse.USER_NOT_FOUND.name()));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
