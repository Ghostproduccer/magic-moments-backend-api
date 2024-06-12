package com.magicmoments.backendapi.service.repositories;

import com.magicmoments.backendapi.model.ERole;
import com.magicmoments.backendapi.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query("SELECT r FROM Roles r WHERE r.name = :roleName")
    Roles findByName(@Param("roleName") ERole name);
}
