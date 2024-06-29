package com.backend.repository;

import com.backend.dto.response.VoucherOrderResponse;
import com.backend.entity.VoucherOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherOrderRepository extends JpaRepository<VoucherOrder, Long> {

    Page<VoucherOrder> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable);

    Page<VoucherOrderResponse> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(Pageable pageable, String code, String name);
}
