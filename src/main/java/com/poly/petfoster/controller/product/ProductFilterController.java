package com.poly.petfoster.controller.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.product.ProductFilterService;

@RestController
@RequestMapping("/api/filter-product/")
public class ProductFilterController {

    @Autowired
    ProductFilterService productFilterService;

    @GetMapping()
    public ResponseEntity<ApiResponse> filterProducts(
            @RequestParam("typeName") Optional<String> typeName,
            @RequestParam("minPrice") Optional<Double> minPrice,
            @RequestParam("maxPrice") Optional<Double> maxPrice,
            @RequestParam("stock") Optional<Boolean> stock,
            @RequestParam("brand") Optional<String> brand,
            @RequestParam("productName") Optional<String> productName,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(productFilterService.filterProducts(typeName, minPrice, maxPrice, stock, brand,
                productName, sort, page));
    }

}
