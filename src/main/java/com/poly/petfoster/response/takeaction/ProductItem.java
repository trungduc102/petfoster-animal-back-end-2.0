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
public class ProductItem {

    private String id;
    private String brand;
    private Integer discount;
    private String image;
    private String name;
    // private Integer rating;
    private Double rating;
    private Object size;
    private Integer oldPrice;
    private Integer price;
    private Integer reviews;
    private List<ReviewItem> reviewItems;
   
}
