package com.example.EcommerceApplication.controller;

import com.example.EcommerceApplication.facade.ExcelExportFacade;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AnalyticController {
    private final ExcelExportFacade excelExportFacade;
    @GetMapping("/order-details")
    public void downloadSalesDetails(HttpServletResponse response) throws IOException {
        ByteArrayInputStream data= excelExportFacade.exportOrderAndStock();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=shop-report.xlsx");
        IOUtils.copy(data, response.getOutputStream());
    }
}
