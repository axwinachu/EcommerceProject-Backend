package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.entity.Wishlist;
import com.example.EcommerceApplication.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }
    public Optional<Wishlist> findByUser(User user){
        return wishlistRepository.findByUser(user);
    }
    public Wishlist save(Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }
    public void delete(Wishlist wishlist){
        wishlistRepository.delete(wishlist);
    }
}
