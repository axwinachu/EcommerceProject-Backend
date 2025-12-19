package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Order;
import com.example.EcommerceApplication.entity.User;
import com.example.EcommerceApplication.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public Order save(Order order){
        return orderRepository.save(order);
    }
    public List<Order> getOrderByUser(User user){
        return orderRepository.findByUser(user);
    }
}
