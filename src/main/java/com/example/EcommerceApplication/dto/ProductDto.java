package com.example.EcommerceApplication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String imageUrl;
}
