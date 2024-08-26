package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.PetBreed;

public interface PetBreedRepository extends JpaRepository<PetBreed, String> {
    // @Query(nativeQuery = true, value = "select * from pet_breed where breed_name= :name ")
    // public Optional<List<PetBreed>> findbyName(@Param("name") String name);

}
