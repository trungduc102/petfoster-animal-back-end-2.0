package com.poly.petfoster.response.productfilter;

import java.util.List;

import com.poly.petfoster.entity.Product;
import com.poly.petfoster.response.takeaction.ProductItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmicProductFilterResponse {
    
    List<Product> filterProducts;
    private Integer pages;
}
