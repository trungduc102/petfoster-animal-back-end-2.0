package com.poly.petfoster.service.impl.brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Brand;
import com.poly.petfoster.repository.BrandRepository;
import com.poly.petfoster.request.brand.BrandRequest;
import com.poly.petfoster.request.brand.CreateBrandRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.brand.BrandResponse;
import com.poly.petfoster.service.admin.brand.BrandService;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    public ApiResponse getAllBrand() {

        List<Brand> listBrand = brandRepository.findAll();
        if (listBrand.isEmpty()) {
            return ApiResponse.builder()
                    .message("No brands data available!")
                    .status(200)
                    .errors(false)
                    .data(new ArrayList<>())
                    .build();
        } else
            return ApiResponse.builder()
                    .message("Successfuly!!!")
                    .status(200)
                    .errors(false)
                    .data(listBrand)
                    .build();
    };

    public ApiResponse createBrand(CreateBrandRequest brand) {
        Map<String, String> errorsMap = new HashMap<>();
        if (brand.getName().isEmpty()) {
            errorsMap.put("brand", "brand can't be blank!");
            return ApiResponse.builder()
                    .message("brand can't be blank!")
                    .status(HttpStatus.NOT_ACCEPTABLE.value())
                    .errors(errorsMap)
                    .build();
        }
        List<Brand> listBrand = brandRepository.findbyName(brand.getName()).orElse(null);
        if (!listBrand.isEmpty()) {
            errorsMap.put("brand", "brand already exits!");
            return ApiResponse.builder()
                    .message("brand already exits!")
                    .status(HttpStatus.FOUND.value())
                    .errors(errorsMap)
                    .build();
        }

        Brand newBrand = Brand.builder()
                .brand(brand.getName())
                .build();

        return ApiResponse.builder()
                .message("Review successfuly!!!")
                .status(200)
                .errors(false)
                .data(brandRepository.save(newBrand))
                .build();
    };

    public ApiResponse updateBrand(Integer id, BrandRequest brandRequest) {
        Map<String, String> errorsMap = new HashMap<>();

        if (!brandRequest.getId().equals(id)) {
            errorsMap.put("brand", "Some thing wrong with Brand ID!");
            return ApiResponse.builder()
                    .message("Some thing wrong with Brand ID!")
                    .status(HttpStatus.CONFLICT.value())
                    .errors(errorsMap)
                    .build();
        }

        if (!brandRepository.existsById(brandRequest.getId())) {
            errorsMap.put("brand", "Can't find Brand ID!");
            return ApiResponse.builder()
                    .message("Can't find Brand ID!")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(errorsMap)
                    .build();
        }

        boolean check = false;
        List<Brand> listBrand = brandRepository.findbyName(brandRequest.getName()).orElse(null);

        for (Brand item : listBrand) {
            if (item.getBrand().equals(brandRequest.getName())) {
                if (!item.getId().equals(brandRequest.getId()) || (!item.getId().equals(brandRequest.getId()))) {
                    check = true;
                }
            }
        }
        if (check) {
            errorsMap.put("brand", "brand already exits!");
            return ApiResponse.builder()
                    .message("brand already exits!")
                    .status(HttpStatus.FOUND.value())
                    .errors(errorsMap).build();
        }

        Brand updateBrand = brandRepository.findById(id).orElse(null);
        updateBrand.setBrand(brandRequest.getName());

        // brandRepository.save(updateBrand);

        return ApiResponse.builder()
                .message("Review successfuly!!!")
                .status(200)
                .errors(false)
                .data(brandRepository.save(updateBrand))
                .build();
    };

    public ApiResponse deleteBrand(Integer id) {
        Map<String, String> errorsMap = new HashMap<>();

        if (!brandRepository.existsById(id)) {
            return ApiResponse.builder()
                    .message("Can't find Brand ID!")
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(errorsMap)
                    .build();
        }
        try {

            Brand curBrand = brandRepository.findById(id).orElse(null);

            curBrand.setDeleted(true);

            return ApiResponse.builder()
                    .message("Query product Successfully")
                    .status(200)
                    .errors(false)
                    .data(brandRepository.save(curBrand))
                    .build();

        } catch (Exception e) {
            errorsMap.put("brand", "Can't delete Brand!");

            return ApiResponse.builder()
                    .message("Can't delete Brand!")
                    .status(HttpStatus.NOT_MODIFIED.value())
                    .errors(errorsMap)
                    .data(null)
                    .build();
        }

    };
}
