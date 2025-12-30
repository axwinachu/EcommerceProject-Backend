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
    @Cacheable("products")
    public List<Product> getAll() {
       return productRepository.findAll();
    }
    @Cacheable(value = "product",key = "#id")
    public Optional<Product> getById(long id){
        return productRepository.findById(id);
    }
    @CacheEvict(value={"products","product"}, allEntries=true)
    public void addProduct(Product newProduct) {
        productRepository.save(newProduct);
    }
    @CacheEvict(value={"products","product"}, allEntries=true)
    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    public void importExcel(List<Product> product) {
        productRepository.saveAll(product);
    }
}
