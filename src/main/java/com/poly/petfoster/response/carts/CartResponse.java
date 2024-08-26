package com.poly.petfoster.response.carts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String id;
    private String brand;
    private Integer size;
    private String image;
    private String name;
    private Double price;
    private Integer quantity;
    private Integer repo;
}
