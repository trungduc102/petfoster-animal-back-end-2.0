package com.poly.petfoster.response.review;

import java.util.List;

import com.poly.petfoster.entity.Review;
import com.poly.petfoster.response.takeaction.ReviewItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailsResponse {
    
    private String id;

    private String name;

    private String image;

    private Double rate;

    private Integer totalRate;

    private DetailRate detailRate;

    //list review of product
    private List<ReviewItem> reviews;

}
