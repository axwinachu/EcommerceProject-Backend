package com.example.EcommerceApplication.mapper;

import com.example.EcommerceApplication.dto.OrderDto;
import com.example.EcommerceApplication.dto.OrderItemDto;
import com.example.EcommerceApplication.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderDto toOrderDto(Order order){
        return OrderDto.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .Status(order.getOrderStatus().name())
                .items(order.getItem().stream().map(item-> OrderItemDto.builder()
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
