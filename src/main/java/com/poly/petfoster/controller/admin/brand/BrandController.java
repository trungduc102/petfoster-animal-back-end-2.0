package com.poly.petfoster.controller.admin.brand;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.brand.BrandRequest;
import com.poly.petfoster.request.brand.CreateBrandRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.brand.BrandService;

@RestController
@RequestMapping("/api/admin/brands/")
public class BrandController {
    @Autowired
    BrandService brandService;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllBrand() {
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createBrand(@Valid @RequestBody CreateBrandRequest name ) {
        return ResponseEntity.ok(brandService.createBrand(name));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateBrand(@PathVariable("id") Integer id, @Valid @RequestBody BrandRequest brandRequest ) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandRequest));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteBrand(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(brandService.deleteBrand(id));
    }
}
