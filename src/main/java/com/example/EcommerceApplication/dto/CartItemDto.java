package com.example.EcommerceApplication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDto {
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private String imageUrl;
}
