package com.poly.petfoster.controller.admin.report;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.report.DashBoardService;

@RestController
@RequestMapping("/api/admin/report/")
public class DashBoardController {
    
    @Autowired
    DashBoardService dashBoardService;

    @GetMapping("daily")
    public ResponseEntity<ApiResponse> dailyReport() {
        return ResponseEntity.ok(dashBoardService.dailyReport());
    }

    @GetMapping("sales-overview")
    public ResponseEntity<ApiResponse> salesOverviews(@RequestParam Optional<Integer> year) {
        return ResponseEntity.ok(dashBoardService.salesOverview(year));
    }

    @GetMapping("product-revenue-by-date")
    public ResponseEntity<ApiResponse> productRevenueByDate(@RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate, @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate) {
        return ResponseEntity.ok(dashBoardService.productRevenueByDate(minDate, maxDate));
    }

}
