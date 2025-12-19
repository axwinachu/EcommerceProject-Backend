package com.example.EcommerceApplication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderDto {
    private String address;
    private String city;
    private String pincode;
}
