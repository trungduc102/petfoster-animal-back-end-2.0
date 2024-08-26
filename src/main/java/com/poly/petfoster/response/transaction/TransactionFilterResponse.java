package com.poly.petfoster.response.transaction;

import com.poly.petfoster.response.common.PagiantionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class TransactionFilterResponse {

    private Object data;
    private Integer pages;
    private Double total;

}
