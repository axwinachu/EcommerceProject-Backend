package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Optional<Cart> findByUserWithItems(User user) {
        return cartRepository.findByUserWithItems(user);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
