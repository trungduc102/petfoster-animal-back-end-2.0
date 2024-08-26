package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
