package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(nativeQuery = true, value = "select * from brand where brand= :name and deleted = 0")
    public Optional<List<Brand>> findbyName(@Param("name") String name);

    @Query("select b from Brand b where deleted = 0 or deleted is null")
    public List<Brand> findAll();

}
