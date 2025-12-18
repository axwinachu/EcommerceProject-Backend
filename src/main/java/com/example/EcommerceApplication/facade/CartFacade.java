package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.CartDto;
import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.CartItem;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.CartMapper;
import com.example.EcommerceApplication.service.CartItemService;
import com.example.EcommerceApplication.service.CartService;
import com.example.EcommerceApplication.service.ProductService;
import com.example.EcommerceApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFacade {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final CartMapper cartMapper;
    private final UserService userService;
    public ResponseEntity<CartDto> addToCart(Long productId) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException("User Not Found"));
        Cart cart=cartService.findByUser(user).orElseGet(()->cartService.save(Cart.builder().user(user).build()));
        Product product=productService.getById(productId)
                .orElseThrow(()->new NotFoundException("Product Not Found"));
        CartItem cartItem=cartItemService.findByCartAndProduct(cart,product)
                .orElse(CartItem.builder().cart(cart).product(product).quantity(0).build());
        cartItem.setQuantity(cartItem.getQuantity()+1);
        cartItemService.save(cartItem);
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> viewCart() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException("User Not Found"));
        Cart cart = cartService.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Cart not found"));
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> updateQuantity(Long productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        Cart cart = cartService.findById(1L)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        Product product = productService.getById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        CartItem cartItem = cartItemService
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotFoundException("Item not in cart"));

        if (quantity == 0) {
            cartItemService.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemService.save(cartItem);
        }

        return new ResponseEntity<>(
                cartMapper.toCartDto(cart),
                HttpStatus.OK
        );
    }

    public ResponseEntity<CartDto> removeItem(Long productId) {
        Cart cart = cartService.findById(1L)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        Product product = productService.getById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        CartItem cartItem = cartItemService
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotFoundException("Item not in cart"));

        cartItemService.delete(cartItem);

        return new ResponseEntity<>(
                cartMapper.toCartDto(cart),
                HttpStatus.OK
        );
    }

}
