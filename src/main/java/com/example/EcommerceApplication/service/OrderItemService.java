package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Order;
import com.example.EcommerceApplication.entity.OrderItem;
import com.example.EcommerceApplication.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }
}
