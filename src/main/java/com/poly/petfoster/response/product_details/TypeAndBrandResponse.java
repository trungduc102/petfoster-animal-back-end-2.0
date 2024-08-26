package com.poly.petfoster.response.product_details;

import java.util.List;

import com.poly.petfoster.entity.Brand;
import com.poly.petfoster.entity.ProductType;
import com.poly.petfoster.response.others.BrandResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeAndBrandResponse {
    private List<ProductType> types;
    private List<BrandResponse> brands;
}
