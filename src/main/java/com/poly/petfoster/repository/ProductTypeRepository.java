package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, String>  {
    
    @Query(nativeQuery = true, value = "select product_type_name from product_type")
    public List<String> getProductTypeNames();

}
