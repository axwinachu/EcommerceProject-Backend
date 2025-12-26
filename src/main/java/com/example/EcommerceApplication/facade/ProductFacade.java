package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.dto.ProductDto;
import com.example.EcommerceApplication.entity.Product;
import com.example.EcommerceApplication.exception.NotFoundException;
import com.example.EcommerceApplication.mapper.ProductMapper;
import com.example.EcommerceApplication.service.ProductService;
import com.example.EcommerceApplication.utils.ExcelHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        Product product=productService.getById(id).orElseThrow(()->new NotFoundException("Product Not Found"));
            ProductDto productDto=productMapper.response(product);
            return new ResponseEntity<>(productDto,HttpStatus.OK);

    }

    public ResponseEntity<String> updateProduct(ProductDto productDto) {
        Optional<Product> existingProduct=productService.getById(productDto.getId());
        if(!existingProduct.isPresent()){
            throw new NotFoundException("Product Not found");
        }else{
            Product updatedProduct=productMapper.transform(productDto);
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
