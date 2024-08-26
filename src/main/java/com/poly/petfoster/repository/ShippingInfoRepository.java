package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.ShippingInfo;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Integer> {
    
}
