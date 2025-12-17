package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
        Optional<Cart> findByUser(User user);
}
