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
    public Optional<Cart> findById(Long id){
        return cartRepository.findById(id);
    }
    public Optional<Cart> findByUser(User user){
        return cartRepository.findByUser(user);
    }
    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
    public void delete(Cart cart){
        cartRepository.delete(cart);
    }

}
