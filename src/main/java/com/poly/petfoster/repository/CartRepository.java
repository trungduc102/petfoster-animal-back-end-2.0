package com.poly.petfoster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Carts;

public interface CartRepository extends JpaRepository<Carts, Integer> {

    @Query(nativeQuery = true, value = "select * from carts where [user_id] = :userId")
    Optional<Carts> findCarts(@Param("userId") String userId);
}
