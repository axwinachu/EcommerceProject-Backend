package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.WishlistDto;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.entity.Wishlist;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.WishlistMapper;
import com.example.EcommerceApplication.service.UserService;
import com.example.EcommerceApplication.service.WishlistItemService;
import com.example.EcommerceApplication.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistFacade {
    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;
    private final UserService userService;
    private final WishlistItemService wishlistItemService;
    private User getLoggedInUser(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        return userService.getByEmail(email).orElseThrow(()-> new NotFoundException("User Not Found"));
    }
    public ResponseEntity<WishlistDto> viewWishlist() {
       User user=getLoggedInUser();
        Wishlist wishlist=wishlistService.findByUser(user)
                .orElseGet(()->wishlistService.save(Wishlist.builder().user(user).build()));
        return new ResponseEntity<>(wishlistMapper.toWishlistDto(wishlist), HttpStatus.OK);
    }
}
