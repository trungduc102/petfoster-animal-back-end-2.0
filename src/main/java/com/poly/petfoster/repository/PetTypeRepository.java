package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.petfoster.entity.PetType;

public interface PetTypeRepository extends JpaRepository<PetType, String> {

}
