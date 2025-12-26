package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.CartDto;
import com.example.EcommerceApplication.facade.CartFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "JWT")
@RequestMapping("/cart")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartFacade cartFacade;
    @PostMapping("/add/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long productId){
         return cartFacade.addToCart(productId);
    }
    @GetMapping("/")
    public ResponseEntity<CartDto> viewCart() {

        return cartFacade.viewCart();
    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<CartDto> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {

        return cartFacade.updateQuantity(productId, quantity);
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDto> removeItem(
            @PathVariable Long productId) {

        return cartFacade.removeItem(productId);
    }
}
