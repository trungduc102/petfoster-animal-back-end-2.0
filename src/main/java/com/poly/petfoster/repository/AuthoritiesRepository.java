package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Authorities;
import com.poly.petfoster.entity.User;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {

    List<Authorities> findByUser(User user);

}
