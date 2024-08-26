package com.poly.petfoster.response.product_details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeAndPrice {

    public Integer size;
    public Double price;
    public Double oldPrice;
    public Integer repo;

}
