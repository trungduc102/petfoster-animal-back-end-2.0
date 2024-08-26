package com.poly.petfoster.response.product_manage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailManageResponse {
    private String id;
    private String name;
    private String type;
    private String brand;
    private Object images;
    private Object repo;
    private String description;
}
