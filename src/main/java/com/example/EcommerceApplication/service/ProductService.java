package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.repository.ProductRepository;
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

    public Optional<Product> getById(long id){
        System.out.println("db hits");
        return productRepository.findById(id);
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
