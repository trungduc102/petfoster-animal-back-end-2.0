package com.poly.petfoster.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Imgs;

public interface ImgsRepository extends JpaRepository<Imgs, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from imgs where product_id = :id")
    public void deleteByProductId(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from imgs where product_id = :id")
    public List<Imgs> getImgsByProductId(@Param("id") String id);

    @Query("select image from Imgs image where image.product.id = :id and image.id = :idImage")
    public Imgs getImageByProductId(@Param("id") String id, @Param("idImage") Integer idImage);

}
