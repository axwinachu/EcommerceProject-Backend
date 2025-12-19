package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.OrderDto;
import com.example.EcommerceApplication.dto.PlaceOrderDto;
import com.example.EcommerceApplication.entity.*;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.OrderMapper;
import com.example.EcommerceApplication.responsce.OrderStatus;
import com.example.EcommerceApplication.service.CartService;
import com.example.EcommerceApplication.service.OrderService;
import com.example.EcommerceApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    String isLogged(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    public ResponseEntity<OrderDto> placeOrder(PlaceOrderDto placeOrderDto) {


        String email=isLogged();

        User user=userService.getByEmail(email)
                .orElseThrow(()->new NotFoundException("user not found"));

        Cart cart=cartService.findByUser(user)
                .orElseThrow(()->new NotFoundException("cart is not found"));

        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        Order order=Order.builder().user(user)
                .address(placeOrderDto.getAddress())
                .city(placeOrderDto.getCity())
                .pincode(placeOrderDto.getPincode())
                .orderStatus(OrderStatus.CREATED)
                .build();

        double total=0;
        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem=OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();
            order.getItem().add(orderItem);
            total+= orderItem.getPrice()* orderItem.getQuantity();
        }
        order.setTotalAmount(total);
        Order saveOrder=orderService.save(order);
        cart.getItems().clear();
        return new ResponseEntity<>(orderMapper.toOrderDto(saveOrder), HttpStatus.OK);
    }

    public ResponseEntity<List<OrderDto>> getMyOrders() {
        String email=isLogged();
        User user=userService.getByEmail(email)
                .orElseThrow(()->new NotFoundException("User is Not Found"));
        List<OrderDto> orders=orderService.getOrderByUser(user).stream().map(orderMapper::toOrderDto).toList();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
