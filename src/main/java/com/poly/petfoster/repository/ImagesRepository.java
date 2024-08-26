package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Imgs;
import com.poly.petfoster.entity.Product;

import java.util.List;


public interface ImagesRepository extends JpaRepository<Imgs, Integer>{
    
    List<Imgs> findByProduct(Product product);
}
