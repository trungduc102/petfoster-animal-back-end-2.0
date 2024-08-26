package com.poly.petfoster.response.recent_view_product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecentViewReponse {
    String id;
    String brand;
    Integer[] size;
    Integer discount;
    String image;
    String name;
    Double oldPrice;
    Double price;
    Integer rating;
}
