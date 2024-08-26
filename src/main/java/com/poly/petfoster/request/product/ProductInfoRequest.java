package com.poly.petfoster.request.product;

import com.poly.petfoster.entity.Brand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfoRequest {
    private String id;
    private String name;
    private Integer brand;
    private String type;
    private String description;
}
