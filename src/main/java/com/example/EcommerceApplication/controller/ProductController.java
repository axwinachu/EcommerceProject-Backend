package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.facade.ProductFacade;
import com.example.EcommerceApplication.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductFacade productFacade;
    @GetMapping("/all")
    public List<ProductDto> getAllProducts(){
        return productFacade.getAllProducts();
    }
    @GetMapping("/{id}")
    public  ProductDto getProductById(@PathVariable long id){
        return productFacade.getProductById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public String updateProduct(@RequestBody ProductDto productDto){
        return productFacade.updateProduct(productDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity<String> addNewProduct(@RequestBody ProductDto productDto){
        return productFacade.addProduct(productDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("remove/{id}")
    public String deleteProduct(@PathVariable long id){
        return productFacade.deleteProductById(id);
    }
    @PostMapping(value = "/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam MultipartFile multipartFile) throws IOException {
     productFacade.importExcel(multipartFile);
     return "products added successfully";
    }
    @GetMapping(value = "/export")
    public void exportProducts(HttpServletResponse response) throws IOException {
       productFacade.exportExcel(response);
    }
}
