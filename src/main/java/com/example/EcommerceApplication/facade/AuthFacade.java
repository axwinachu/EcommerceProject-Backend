package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.UserDto;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    public ResponseEntity<String> signup(UserDto userDto) {
        boolean existedUser=userService.existsByEmail(userDto.getEmail());
        if(existedUser){
            return new ResponseEntity<>("already have an account", HttpStatus.BAD_REQUEST);
        }
        User newUser=User.builder().name(userDto.getName()).email(userDto.getEmail()).password(userDto.getPassword())
                .role(userDto.getPassword()).active(userDto.isActive()).build();
        userService.save(new User());
        return new ResponseEntity<>("User Created Successfully",HttpStatus.OK);
    }

    public ResponseEntity<?> login(UserDto userDto) {
        String email= userDto.getEmail();
        String password=userDto.getPassword();
        boolean existedUser=userService.existsByEmail(email);
        if(!existedUser){
            return new  ResponseEntity<>("user not found please signup",HttpStatus.OK);
        }
        User validateUser=userService.getByEmail(email).orElseThrow(()->new NotFoundException("invalid credential"));
        if(!passwordEncoder.matches(validateUser.getPassword(), password)){
            return new ResponseEntity<>("invalid Credential",HttpStatus.OK);
        }


    }
}
