package com.example.EcommerceApplication.facade;

import com.example.EcommerceApplication.entity.Order;
import com.example.EcommerceApplication.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExcelExportFacade {

    private final OrderRepository orderRepository;

    public ByteArrayInputStream exportOrderAndStock() throws IOException {
        List<Order> orders = orderRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet orderSheet = workbook.createSheet("Orders");
        Row title = orderSheet.createRow(0);
        title.createCell(0).setCellValue("ID");
        title.createCell(1).setCellValue("User");
        title.createCell(2).setCellValue("Address");
        title.createCell(3).setCellValue("Date");
        title.createCell(4).setCellValue("Status");
        title.createCell(5).setCellValue("Amount");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int rowIndex = 1;
        double totalRevenue = 0;
        for (Order data : orders) {
            Row row = orderSheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getId());
            row.createCell(1).setCellValue(data.getUser().getName());
            row.createCell(2).setCellValue(data.getAddress());
            row.createCell(3).setCellValue(data.getCreatedAt().format(formatter));
            row.createCell(4).setCellValue(data.getOrderStatus().name());
            row.createCell(5).setCellValue(data.getTotalAmount());
            totalRevenue += data.getTotalAmount();
        }
        Row totalRow = orderSheet.createRow(rowIndex);
        totalRow.createCell(4).setCellValue("TOTAL");
        totalRow.createCell(5).setCellValue(totalRevenue);
        for (int i = 0; i <= 5; i++) {
            orderSheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
