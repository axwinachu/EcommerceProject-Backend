package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.entity.Wishlist;
import com.example.EcommerceApplication.entity.WishlistItem;
import com.example.EcommerceApplication.repository.WishlistItemRepository;
import com.example.EcommerceApplication.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistItemService {
    private final WishlistItemRepository wishlistItemRepository;
    public Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product){
        return wishlistItemRepository.findByWishlistAndProduct(wishlist,product);
    }
    public WishlistItem save(WishlistItem wishlistItem){
        return wishlistItemRepository.save(wishlistItem);
    }
    public void delete(WishlistItem wishlistItem){
         wishlistItemRepository.delete(wishlistItem);
    }

}
