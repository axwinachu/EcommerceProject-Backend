package com.example.EcommerceApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistItemDto {
    private Long productId;
    private String productName;
    private double price;
    private String imageUrl;
}
