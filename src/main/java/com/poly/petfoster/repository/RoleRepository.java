package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(nativeQuery = true, value = "select * from role where id = 4")
    public Role getRoleUser();

    @Query(nativeQuery = true, value = "select * from role " +
            "where id in (1, 2)")
    List<Role> managementRoles();

}
