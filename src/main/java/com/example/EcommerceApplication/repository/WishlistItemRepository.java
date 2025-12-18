package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.entity.Wishlist;
import com.example.EcommerceApplication.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {
    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);
}
