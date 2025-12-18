package com.example.EcommerceApplication.dto;

import com.example.EcommerceApplication.entity.WishlistItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistDto {
    private List<WishlistItemDto> items;
    private  int size;
}
