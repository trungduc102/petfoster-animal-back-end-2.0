package com.poly.petfoster.controller.transacton;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.transaction.TransactionBaseRequest;
import com.poly.petfoster.request.transaction.TransactionRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.transaction.TransactionService;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Object> setTransactionToDB(@RequestBody TransactionBaseRequest transactionBaseRequest) {
        System.out.println(transactionBaseRequest.toString());
        return ResponseEntity.ok(transactionService.setTransactionToDB(transactionBaseRequest.getData()));
    }

    @GetMapping("")
    public ResponseEntity<Object> getTransactions(@RequestParam(value = "page") Optional<Integer> page) {
        return ResponseEntity.ok(transactionService.getTransaction((page)));
    }
}
