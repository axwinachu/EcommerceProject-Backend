package com.example.EcommerceApplication.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String role;
    private boolean active=true;
}
