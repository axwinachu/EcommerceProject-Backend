package com.example.EcommerceApplication.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${security.secret}")
    private String SECRET;
    @Value("${security.expiration}")
    private int EXPIRATION;
    private Key SECRETKEY;
    @PostConstruct
    public void init(){
        this.SECRETKEY= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(SECRETKEY, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRETKEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String extractRole(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(SECRETKEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }
    public Boolean verifyToken(String token){
        try{
            extractEmail(token);
            return true;
        }catch (Exception e){
            throw new RuntimeException("token is not valid");
        }

    }
}
