package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.AuthResponseDto;
import com.example.EcommerceApplication.dto.UserDto;
import com.example.EcommerceApplication.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthFacade authFacade;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto){
        return authFacade.signup(userDto);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto userDto){
        return authFacade.login(userDto);
    }
}
