package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.exception.ProductNotFoundException;
import com.example.EcommerceApplication.repository.ProductRepository;
import com.example.EcommerceApplication.responsce.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        System.out.println("db hits");
       return productRepository.findAll();
    }

    public Product getById(long id){
        System.out.println("db hits");
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(ProductResponse.PRODUCT_NOT_FOUND.name()));
    }

    public void addProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    public void importExcel(List<Product> product) {
        productRepository.saveAll(product);
    }
}
