package com.example.EcommerceApplication.repository;

import com.example.EcommerceApplication.entity.Order;
import com.example.EcommerceApplication.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
