package com.example.EcommerceApplication.mapper;

import com.example.EcommerceApplication.dto.WishlistDto;
import com.example.EcommerceApplication.dto.WishlistItemDto;
import com.example.EcommerceApplication.entity.Wishlist;
import com.example.EcommerceApplication.entity.WishlistItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WishlistMapper {
    public WishlistDto toWishlistDto(Wishlist wishlist){
        List<WishlistItemDto> items=wishlist.getItems().stream().map(this::toItemDto).toList();
        return WishlistDto.builder().items(items).size(items.size()).build();
    }
    public WishlistItemDto toItemDto(WishlistItem wishlistItem){
        return WishlistItemDto.builder().productId(wishlistItem.getProduct().getId())
                .productName(wishlistItem.getProduct().getName())
                .price(wishlistItem.getProduct().getPrice())
                .imageUrl(wishlistItem.getProduct().getImageUrl())
                .build();
    }
}
