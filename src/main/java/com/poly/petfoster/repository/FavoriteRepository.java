package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    // where f.user.id = :userId and f.pet.petId = :petId
    @Query(value = "select favorite from Favorite favorite where favorite.user.id = :userId and favorite.pet.petId = :petId")
    Favorite existByUserAndPet(@Param("userId") String userId, @Param("petId") String petId);

}
