package com.poly.petfoster.service.admin.product;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.poly.petfoster.request.CreateProductRequest;
import com.poly.petfoster.request.product.ProductInfoRequest;
import com.poly.petfoster.request.product.ProductRequest;
import com.poly.petfoster.response.ApiResponse;

public interface ProductService {
    ApiResponse getProduct(String id);

    ApiResponse getAllProduct(Optional<Integer> page);

    ApiResponse getProductInfo(String id);

    ApiResponse updateProductWithInfo(String id, ProductInfoRequest productInfoRequest);

    ApiResponse updateProduct(String id, ProductRequest updateProductReq);

    ApiResponse deleteProduct(String id);

    ApiResponse createProduct(CreateProductRequest createProductRequest, List<MultipartFile> images);

}
