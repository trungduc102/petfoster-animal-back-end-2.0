package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Brand;

public interface BrandRespository extends JpaRepository<Brand, Integer> {
    
}
