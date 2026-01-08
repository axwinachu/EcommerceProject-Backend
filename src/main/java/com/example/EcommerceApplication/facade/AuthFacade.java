package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.AuthResponseDto;
import com.example.EcommerceApplication.dto.LoginDto;
import com.example.EcommerceApplication.dto.UserDto;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.UserNotFoundException;
import com.example.EcommerceApplication.responsce.AuthResponse;
import com.example.EcommerceApplication.security.JwtUtil;
import com.example.EcommerceApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public String signup(UserDto userDto) {
        boolean existedUser=userService.existsByEmail(userDto.getEmail());
        if(existedUser){
            return AuthResponse.INVALID_CREDENTIAL.name();

        }
        User newUser=User.builder().name(userDto.getName()).email(userDto.getEmail()).password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole().toUpperCase()).active(true).build();
        userService.save(newUser);
        return AuthResponse.USER_CREATED_SUCCESSFULLY.name();
    }

    public AuthResponseDto login(LoginDto loginDto) {
        String email= loginDto.getEmail();
        String password=loginDto.getPassword();
        boolean existedUser=userService.existsByEmail(email);
        if(!existedUser){
            throw new UserNotFoundException(AuthResponse.USER_NOT_FOUND.name());
        }
        User validateUser=userService.getByEmail(email);
        if(!passwordEncoder.matches( password,validateUser.getPassword())){
            throw  new UserNotFoundException(AuthResponse.INVALID_CREDENTIAL.name());
        }
       String token=jwtUtil.generateToken(email, validateUser.getRole());
        AuthResponseDto responseDto=AuthResponseDto.builder().token(token).build();
        return responseDto;

    }
}
