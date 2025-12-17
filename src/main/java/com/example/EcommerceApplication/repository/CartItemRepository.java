package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.CartItem;
import com.example.EcommerceApplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
