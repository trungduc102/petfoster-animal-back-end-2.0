package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.DeliveryCompany;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Integer> {
    
}
