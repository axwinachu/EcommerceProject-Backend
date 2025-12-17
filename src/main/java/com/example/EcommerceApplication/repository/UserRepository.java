package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String Email);
    boolean existsByEmail(String Email);
}
