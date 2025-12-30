package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.OrderDto;
import com.example.EcommerceApplication.dto.PlaceOrderDto;
import com.example.EcommerceApplication.entity.*;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.OrderMapper;
import com.example.EcommerceApplication.responsce.CartResponse;
import com.example.EcommerceApplication.responsce.OrderStatus;
import com.example.EcommerceApplication.responsce.UserResponse;
import com.example.EcommerceApplication.service.CartService;
import com.example.EcommerceApplication.service.OrderService;
import com.example.EcommerceApplication.service.UserService;
import jakarta.transaction.Transactional;
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
    private String isLogged(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    @Transactional
    public ResponseEntity<OrderDto> placeOrder(PlaceOrderDto placeOrderDto) {


        String email=isLogged();

        User user=userService.getByEmail(email)
                .orElseThrow(()->new NotFoundException(UserResponse.USER_NOT_FOUND.name()));

        Cart cart=cartService.findByUser(user)
                .orElseThrow(()->new NotFoundException(CartResponse.CART_NOT_FOUND.name()));

        if(cart.getItems().isEmpty()){
            throw new RuntimeException(OrderStatus.CART_IS_EMPTY.name());//new except
        }

        Order order=Order.builder().user(user)
                .address(placeOrderDto.getAddress())
                .city(placeOrderDto.getCity())
                .pincode(placeOrderDto.getPincode())
                .orderStatus(OrderStatus.CREATED)
                .build();

        double total=0;
        for(CartItem cartItem: cart.getItems()){

            Product product=cartItem.getProduct();
            if(product.getStock()< cartItem.getQuantity()){
                throw new RuntimeException("Not Enough stock"+product.getName());//custom except
            }
            product.setStock(product.getStock()- cartItem.getQuantity());

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
                .orElseThrow(()->new NotFoundException(UserResponse.USER_NOT_FOUND.name()));
        List<OrderDto> orders=orderService.getOrderByUser(user).stream().map(orderMapper::toOrderDto).toList();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
