package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.facade.ProductFacade;
import com.example.EcommerceApplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductFacade productFacade;
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return productFacade.getAllProducts();
    }
    @GetMapping("/id/{id}")
    public  ResponseEntity<ProductDto> getProductById(@PathVariable long id){
        return productFacade.getProductById(id);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto productDto){
        return productFacade.updateProduct(productDto);
    }
    @PostMapping("/addProduct")
    public ResponseEntity<String> addNewProduct(@RequestBody ProductDto productDto){
        return productFacade.addProduct(productDto);
    }
    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        return productFacade.deleteProductById(id);
    }
}
