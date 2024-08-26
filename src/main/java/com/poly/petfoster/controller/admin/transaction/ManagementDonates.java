package com.poly.petfoster.controller.admin.transaction;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.transaction.TransactionService;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/admin/donates")
public class ManagementDonates {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> filter(@RequestParam("search") Optional<String> search,
            @RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate,
            @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(transactionService.filterTransaction(search, minDate, maxDate, sort, page));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse> report() {
        return ResponseEntity.ok(transactionService.report());
    }

}
