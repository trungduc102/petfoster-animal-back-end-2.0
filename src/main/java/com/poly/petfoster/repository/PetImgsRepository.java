package com.poly.petfoster.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.PetImgs;

public interface PetImgsRepository extends JpaRepository<PetImgs, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from pet_imgs where pet_id = :id")
    public void deleteByPetId(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from pet_imgs where pet_id = :id")
    public List<PetImgs> getImgsBypetId(@Param("id") String id);

    @Query("select image from PetImgs image where image.pet.id = :id and image.id = :idImage")
    public PetImgs getImageByPetId(@Param("id") String id, @Param("idImage") Integer idImage);

    @Query("select image from PetImgs image where image.pet.id = :id and image.nameImg = :name")
    public PetImgs findByNameImg(@Param("id") String id, @Param("name") String name);

}