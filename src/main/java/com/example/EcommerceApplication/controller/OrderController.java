package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.OrderDto;
import com.example.EcommerceApplication.dto.PlaceOrderDto;
import com.example.EcommerceApplication.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderFacade orderFacade;

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return orderFacade.placeOrder(placeOrderDto);
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders(){
        return orderFacade.getMyOrders();
    }
}
