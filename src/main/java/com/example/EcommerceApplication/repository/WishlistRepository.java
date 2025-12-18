package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    Optional<Wishlist> findByUser(User user);
}
