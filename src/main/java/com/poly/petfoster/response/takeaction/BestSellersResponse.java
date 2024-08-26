package com.poly.petfoster.response.takeaction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BestSellersResponse {
    private List<ProductItem> data;
    private Integer pages;
}
