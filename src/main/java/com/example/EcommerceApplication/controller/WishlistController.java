package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.WishlistDto;
import com.example.EcommerceApplication.facade.WishlistFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistFacade wishlistFacade;
    @GetMapping("/")
    public ResponseEntity<WishlistDto> viewWishlist(){
       return wishlistFacade.viewWishlist();
    }
}
