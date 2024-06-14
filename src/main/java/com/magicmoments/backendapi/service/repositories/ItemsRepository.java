package com.magicmoments.backendapi.service.repositories;

import com.magicmoments.backendapi.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {
    @Query("SELECT i FROM Items i WHERE i.id = :itemId")
    Items findById(@Param("itemId") int itemId);
}
