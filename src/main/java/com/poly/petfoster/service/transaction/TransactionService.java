package com.poly.petfoster.service.transaction;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.poly.petfoster.request.transaction.TransactionRequest;
import com.poly.petfoster.response.ApiResponse;

public interface TransactionService {

    ApiResponse getTransaction(Optional<Integer> page);

    ApiResponse report();

    ApiResponse filterTransaction(Optional<String> search, Optional<Date> minDate, Optional<Date> maxDate,
            Optional<String> sort, Optional<Integer> page);

    ApiResponse setTransactionToDB(List<TransactionRequest> transactionRequest);
}
