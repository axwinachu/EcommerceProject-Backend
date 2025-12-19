package com.example.EcommerceApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OrderItemDto {
    private String productName;
    private int quantity;
    private double price;
}
