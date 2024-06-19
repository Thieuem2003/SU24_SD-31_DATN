package com.backend.repository;

import com.backend.entity.Shoe;
import com.backend.entity.ShoeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeDetailRepository extends JpaRepository<ShoeDetail, Long> {
    @Query("SELECT sh FROM ShoeDetail sh WHERE sh.status=0")
    List<ShoeDetail> getShoeDetailPending();

    @Query("SELECT sh FROM ShoeDetail sh WHERE sh.status=1")
    List<ShoeDetail> getAllShoeDetailActive();

    List<ShoeDetail> getShoeDetailByShoe(Shoe shoe);

}
