package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.ProductMapper;
import com.example.EcommerceApplication.responsce.ProductResponse;
import com.example.EcommerceApplication.service.ProductService;
import com.example.EcommerceApplication.utils.ExcelHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ExcelHelper excelHelper;
    @CacheEvict(value={"products","product"}, allEntries=true)
    public  ResponseEntity<String> addProduct(ProductDto productDto) {
        Product newProduct=Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .stock(productDto.getStock())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .build();
        productService.addProduct(newProduct);
        return new ResponseEntity<>(ProductResponse.ITEM_ADDED_SUCCESSFULLY.name(),HttpStatus.OK);
    }
    @Cacheable("products")
    public List<ProductDto> getAllProducts() {
        List<Product> product = productService.getAll();
        if (product.isEmpty()) {
            throw new NotFoundException(ProductResponse.PRODUCT_NOT_FOUND.name());
        } else {
            List<ProductDto> products = product.stream()
                    .map(p -> productMapper.response(p)).collect(Collectors.toList());
            return products;
        }
    }
    @Cacheable(value = "product",key = "#id")
    public ProductDto getProductById(long id) {
        Product product=productService.getById(id).orElseThrow(()->new NotFoundException(ProductResponse.PRODUCT_NOT_FOUND.name()));
            ProductDto productDto=productMapper.response(product);
            return productDto;
    }
    @CacheEvict(value={"products","product"}, allEntries=true)
    public String updateProduct(ProductDto productDto) {
            productService.getById(productDto.getId()).orElseThrow(()->new NotFoundException(ProductResponse.PRODUCT_NOT_FOUND.name()));
            Product updatedProduct=productMapper.transform(productDto);
            productService.addProduct(updatedProduct);
            return ProductResponse.PRODUCT_UPDATED_SUCCESSFULLY.name();
    }
    @CacheEvict(value={"products","product"}, allEntries=true)
    public String deleteProductById(long id) {
        productService.getById(id).orElseThrow(()->new NotFoundException(ProductResponse.PRODUCT_NOT_FOUND.name()));
        productService.removeProduct(id);
        return ProductResponse.DELETED_SUCCESSFULLY.name();
    }
    public void importExcel(MultipartFile multipartFile) throws IOException {
        List<Product> product=excelHelper.excelToProduct(multipartFile.getInputStream());
        productService.importExcel(product);
    }
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
        List<Product> products = productService.getAll();
        ByteArrayInputStream excelStream = excelHelper.productToExcel(products);
        IOUtils.copy(excelStream, response.getOutputStream());
    }
}
