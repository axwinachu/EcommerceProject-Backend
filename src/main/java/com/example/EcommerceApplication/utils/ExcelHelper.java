package com.example.EcommerceApplication.utils;

import com.example.EcommerceApplication.entity.Product;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelHelper {
    public List<Product> excelToProduct(InputStream inputStream) throws IOException {
      try(Workbook workbook=new XSSFWorkbook(inputStream)){
          Sheet sheet=workbook.getSheetAt(0);
          List<Product> products=new ArrayList<>();
          for(int i=1;i<=sheet.getLastRowNum();i++){
              Row row=sheet.getRow(i);
              Product product=Product.builder()
                      .name(row.getCell(0).getStringCellValue())
                      .description(row.getCell(1).getStringCellValue())
                      .price(row.getCell(2).getNumericCellValue())
                      .stock((int)row.getCell(3).getNumericCellValue())
                      .imageUrl(row.getCell(4).getStringCellValue()).build();
              products.add(product);
          }
          return products;
      }catch (Exception e){
          throw  new RuntimeException("invalid excel file");
      }
    }
    public ByteArrayInputStream productToExcel(List<Product> products){
        try(Workbook workbook=new XSSFWorkbook();ByteArrayOutputStream out=new ByteArrayOutputStream()) {
            Sheet sheet=workbook.createSheet("Products");

            Row header=sheet.createRow(0);
            String[] headers={"Name","Description","Price","Stock","ImageUrl"};

            for(int i=0;i<headers.length;i++){
                header.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx=1;
            for(Product p :products){
                Row row=sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getName());
                row.createCell(1).setCellValue(p.getDescription());
                row.createCell(2).setCellValue(p.getPrice());
                row.createCell(3).setCellValue(p.getStock());
                row.createCell(4).setCellValue(p.getImageUrl());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
