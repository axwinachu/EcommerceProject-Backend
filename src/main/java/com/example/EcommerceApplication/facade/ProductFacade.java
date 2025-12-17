package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.ProductMapper;
import com.example.EcommerceApplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;
    private final ProductMapper productMapper;
    public  ResponseEntity<String> addProduct(ProductDto productDto) {
        Product newProduct=Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .stock(productDto.getStock())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .build();
        productService.addProduct(newProduct);
        return new ResponseEntity<>("Added Successfully",HttpStatus.OK);

    }

    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> product = productService.getAll();
        if (product.isEmpty()) {
            throw new NotFoundException("Product Not Found");
        } else {
            List<ProductDto> products = product.stream()
                    .map(p -> productMapper.response(p)).collect(Collectors.toList());
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<ProductDto> getProductById(long id) {
        Optional<Product> product=productService.getById(id);
        if(!product.isPresent()){
            throw new NotFoundException("Product Not found");
        }else{
            ProductDto productDto=productMapper.response(product.get());
            return new ResponseEntity<>(productDto,HttpStatus.OK);
        }
    }

    public ResponseEntity<String> updateProduct(ProductDto productDto) {
        Optional<Product> existingProduct=productService.getById(productDto.getId());
        if(!existingProduct.isPresent()){
            throw new NotFoundException("Product Not found");
        }else{
            Product updatedProduct=productMapper.Transformer(productDto);
            productService.addProduct(updatedProduct);
            return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
        }
    }
    public ResponseEntity<String> deleteProductById(long id) {
        Optional<Product> existingProduct=productService.getById(id);
        if(!existingProduct.isPresent()){
            throw new NotFoundException("Product Not found");
        }
        productService.removeProduct(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);

    }
}
