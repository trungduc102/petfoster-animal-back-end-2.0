package com.poly.petfoster.service.product;

import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface ProductFilterService {
    public ApiResponse filterProducts(Optional<String> typeName, Optional<Double> minPrice, Optional<Double> maxPrice, Optional<Boolean> stock, Optional<String> brand, Optional<String> productName, Optional<String> sort, Optional<Integer> page);
    public ApiResponse filterAdminProducts(Optional<String> keyword,Optional<String> typeName,  Optional<String> brand, Optional<String> sort, Optional<Integer> page, Boolean isActive);

}
