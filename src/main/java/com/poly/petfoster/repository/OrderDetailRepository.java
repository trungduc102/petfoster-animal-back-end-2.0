package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Product;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
    // List<OrderDetail> findByProduct(Product product);

}
