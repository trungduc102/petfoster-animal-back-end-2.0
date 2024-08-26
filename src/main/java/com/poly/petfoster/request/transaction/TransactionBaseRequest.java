package com.poly.petfoster.request.transaction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionBaseRequest {
    private Integer error;
    private List<TransactionRequest> data;
}
