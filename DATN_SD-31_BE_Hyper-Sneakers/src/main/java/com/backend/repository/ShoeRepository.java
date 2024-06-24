package com.backend.repository;

import com.backend.entity.Shoe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {
    @Query("SELECT s FROM Shoe s WHERE lower(s.name) LIKE lower(concat('%', :searchNameOrCode,'%')) OR lower(s.code) LIKE lower(concat('%', :searchNameOrCode,'%'))")
    Page<Shoe> searchByNameOrCode(String searchNameOrCode, Pageable pageable);
}
