package com.poly.petfoster.service.product;

import com.poly.petfoster.response.ApiResponse;

public interface ProductDetailsService {
    
    public ApiResponse productDetails(String id);

    public ApiResponse getProductTypesAndBrands();

}
