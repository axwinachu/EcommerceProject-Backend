package com.example.EcommerceApplication.mapper;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.entity.Product;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class ProductMapper {
    public ProductDto response(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .build();
    }
    public Product Transformer(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .imageUrl(productDto.getImageUrl())
                .build();
    }
}
