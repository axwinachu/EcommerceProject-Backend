package com.example.EcommerceApplication.entity;

import com.example.EcommerceApplication.responsce.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Double totalAmount;

    private String address;
    private String city;
    private String pincode;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    private List<OrderItem> item=new ArrayList<>();


}
