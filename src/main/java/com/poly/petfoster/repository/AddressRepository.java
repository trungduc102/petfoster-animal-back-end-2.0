package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Addresses;
import com.poly.petfoster.entity.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Addresses, Integer> {

    @Query("select a from Addresses a where a.isDefault = true and a.user.username= :username")
    Addresses findByIsDefaultWithUser(@Param("username") String username);

    List<Addresses> findByIsDefault(Boolean isDefault);

    List<Addresses> findByUser(User user);

    @Query("select a from Addresses a where a.id = :id and a.user.username= :username")
    Addresses findByIdAndUser(@Param("id") Integer id, @Param("username") String username);
}
