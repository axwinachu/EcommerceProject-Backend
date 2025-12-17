package com.example.EcommerceApplication.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {
    private List<CartItemDto> items;
    private double totalAmount;
}
