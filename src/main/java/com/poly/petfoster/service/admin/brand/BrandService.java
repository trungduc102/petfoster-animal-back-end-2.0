package com.poly.petfoster.service.admin.brand;

import com.poly.petfoster.request.brand.BrandRequest;
import com.poly.petfoster.request.brand.CreateBrandRequest;
import com.poly.petfoster.response.ApiResponse;

public interface BrandService {
    ApiResponse getAllBrand();

    ApiResponse createBrand(CreateBrandRequest name);

    ApiResponse updateBrand(Integer id, BrandRequest brandRequest);

    ApiResponse deleteBrand(Integer id);
}
