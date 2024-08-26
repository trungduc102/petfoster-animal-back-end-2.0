package com.poly.petfoster.response.order_history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersFilterResponse {
    
    private List<OrderFilterResponse> orderFilters;

    private Integer pages;

}
