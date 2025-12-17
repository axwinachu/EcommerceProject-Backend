package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.CartItem;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public Optional<CartItem> findByCartAndProduct(Cart cart, Product product){
        return cartItemRepository.findByCartAndProduct(cart, product);
    }
    public CartItem save(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
    public void delete(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }

}
