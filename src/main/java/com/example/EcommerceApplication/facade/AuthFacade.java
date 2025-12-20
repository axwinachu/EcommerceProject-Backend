package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.UserDto;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.responsce.AuthResponse;
import com.example.EcommerceApplication.security.JwtUtil;
import com.example.EcommerceApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public ResponseEntity<String> signup(UserDto userDto) {
        boolean existedUser=userService.existsByEmail(userDto.getEmail());
        if(existedUser){
            return new ResponseEntity<>(AuthResponse.INVALID_CREDENTIAL.name(), HttpStatus.BAD_REQUEST);
        }
        User newUser=User.builder().name(userDto.getName()).email(userDto.getEmail()).password(passwordEncoder.encode(userDto.getPassword()))
                .role("ROLE_"+userDto.getRole().toUpperCase()).active(userDto.isActive()).build();
        userService.save(newUser);
        return new ResponseEntity<>(AuthResponse.USER_CREATED_SUCCESSFULLY.name(),HttpStatus.OK);
    }

    public ResponseEntity<?> login(UserDto userDto) {
        String email= userDto.getEmail();
        String password=userDto.getPassword();
        boolean existedUser=userService.existsByEmail(email);
        if(!existedUser){
            return new  ResponseEntity<>(AuthResponse.USER_NOT_FOUND.name(),HttpStatus.OK);
        }
        User validateUser=userService.getByEmail(email).orElseThrow(()->new NotFoundException(AuthResponse.INVALID_CREDENTIAL.name()));
        if(!passwordEncoder.matches( password,validateUser.getPassword())){
            return new ResponseEntity<>(AuthResponse.INVALID_CREDENTIAL.name(),HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("token:",jwtUtil.generateToken(email,password)),HttpStatus.OK);

    }
}
