package com.example.EcommerceApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long orderId;
    private double totalAmount;
    private String Status;
    private List<OrderItemDto> items;
}
