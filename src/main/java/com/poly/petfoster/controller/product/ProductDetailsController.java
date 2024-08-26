package com.poly.petfoster.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.product.ProductDetailsService;

@RestController
@RequestMapping("/api/product/")
public class ProductDetailsController {
    
    @Autowired
    ProductDetailsService productDetailsService;

    @GetMapping("detail/{id}")
    public ResponseEntity<ApiResponse> productDetails(@PathVariable String id) {
        return ResponseEntity.ok(productDetailsService.productDetails(id));
    }

    @GetMapping("types-brands")
    public ResponseEntity<ApiResponse> getProductTypesAndBrands() {
        return ResponseEntity.ok(productDetailsService.getProductTypesAndBrands());
    }

}
