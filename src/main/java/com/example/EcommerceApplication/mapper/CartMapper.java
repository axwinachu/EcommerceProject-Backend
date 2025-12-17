package com.example.EcommerceApplication.mapper;

import com.example.EcommerceApplication.dto.CartDto;
import com.example.EcommerceApplication.dto.CartItemDto;
import com.example.EcommerceApplication.entity.Cart;
import com.example.EcommerceApplication.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {
    public CartItemDto toItemDto(CartItem item) {
        return CartItemDto.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .imageUrl(item.getProduct().getImageUrl())
                .build();
    }
    public CartDto toCartDto(Cart cart) {
        List<CartItemDto> items = cart.getItems()
                .stream()
                .map(this::toItemDto)
                .toList();

        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        return CartDto.builder()
                .items(items)
                .totalAmount(total)
                .build();
    }
}
