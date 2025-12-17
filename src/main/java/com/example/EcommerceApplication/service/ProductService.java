package com.example.EcommerceApplication.service;

import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAll() {
       return productRepository.findAll();
    }
    public Optional<Product> getById(long id){
        return productRepository.findById(id);
    }

    public void addProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }
}
