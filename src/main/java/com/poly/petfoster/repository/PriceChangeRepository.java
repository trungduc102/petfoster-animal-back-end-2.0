package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.PriceChange;

public interface PriceChangeRepository extends JpaRepository<PriceChange, Integer> {

    @Query("select pc from PriceChange pc where pc.productRepo.product.id = :idProduct and pc.productRepo.isActive = true")
    List<PriceChange> findByProductId(@Param("idProduct") String idProduct);

}
