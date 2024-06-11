package com.magicmoments.backendapi.service.repositories;

import com.magicmoments.backendapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.id = :username")
    Optional<Users> findByUsername(@Param("username") String username);
}
