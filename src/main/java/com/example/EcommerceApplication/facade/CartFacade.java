package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.CartDto;
import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.CartItem;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.CartMapper;
import com.example.EcommerceApplication.responsce.AuthResponse;
import com.example.EcommerceApplication.responsce.CartResponse;
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
    String getUserEmail(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();

    }
    public ResponseEntity<CartDto> addToCart(Long productId) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException(AuthResponse.USER_NOT_FOUND.name()));
        Cart cart=cartService.findByUser(user).orElseGet(()->cartService.save(Cart.builder().user(user).build()));
        Product product=productService.getById(productId)
                .orElseThrow(()->new NotFoundException(CartResponse.PRODUCT_NOT_FOUND.name()));

        CartItem cartItem=cartItemService.findByCartAndProduct(cart,product)
                .orElse(CartItem.builder().cart(cart).product(product).quantity(0).build());
        
        cartItem.setQuantity(cartItem.getQuantity()+1);
        cartItemService.save(cartItem);
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> viewCart() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException(AuthResponse.USER_NOT_FOUND.name()));
        Cart cart = cartService.findByUser(user)
                .orElseGet(() -> cartService.save(Cart.builder().user(user).build()));
        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.OK);
    }

    public ResponseEntity<CartDto> updateQuantity(Long productId, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(CartResponse.QUANTITY_CANNOT_BE_NEGATIVE.name());
        }
        String email=getUserEmail();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException(AuthResponse.USER_NOT_FOUND.name()));
        Cart cart = cartService.findByUser(user)
                .orElseThrow(() -> new NotFoundException(CartResponse.CART_NOT_FOUND.name()));

        Product product = productService.getById(productId)
                .orElseThrow(() -> new NotFoundException(CartResponse.PRODUCT_NOT_FOUND.name()));

        CartItem cartItem = cartItemService
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotFoundException(CartResponse.PRODUCT_NOT_FOUND.name()));

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
        String email=getUserEmail();
        User user=userService.getByEmail(email).orElseThrow(()->new NotFoundException(AuthResponse.USER_NOT_FOUND.name()));
        Cart cart = cartService.findByUser(user)
                .orElseThrow(() -> new NotFoundException(CartResponse.CART_NOT_FOUND.name()));

        Product product = productService.getById(productId)
                .orElseThrow(() -> new NotFoundException(CartResponse.PRODUCT_NOT_FOUND.name()));

        CartItem cartItem = cartItemService
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotFoundException(CartResponse.ITEM_NOT_IN_CART.name()));

        cartItemService.delete(cartItem);

        return new ResponseEntity<>(
                cartMapper.toCartDto(cart),
                HttpStatus.OK
        );
    }

}
