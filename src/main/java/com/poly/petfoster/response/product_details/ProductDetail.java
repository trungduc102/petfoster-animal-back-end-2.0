package com.poly.petfoster.response.product_details;

import java.util.List;

import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.response.takeaction.ReviewItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    
    public String id;
    public String brand;
    public Integer discount;
    public String image;
    public String name;
    public Double rating;
    public List<String> images;
    public String desciption;
    public List<SizeAndPrice> sizeAndPrice;
    public List<ProductItem> suggestions;
    private Integer reviews;
    private List<ReviewItem> reviewItems; 

}
