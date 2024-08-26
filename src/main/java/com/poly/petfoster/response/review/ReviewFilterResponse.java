package com.poly.petfoster.response.review;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFilterResponse {
    
    private String productId;

    private String productName;

    private String image;

    private Double rate;

    // private String lastest;
    private Date lastest;
    
    private Integer reviews;

    private Integer commentNoRep;

}
