package com.backend.service;

import com.backend.dto.request.VoucherOrderRequest;
import com.backend.dto.response.VoucherOrderResponse;
import com.backend.entity.VoucherOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IVoucherOrderService {

    Page<VoucherOrderResponse> getAllVouchers(Pageable pageable, String searchKeyword);

    Optional<VoucherOrderResponse> getVoucherById(Long id);

    VoucherOrder saveVoucher(VoucherOrder voucherOrder);

    void deleteVoucher(Long id);

    VoucherOrder convertToVoucherOrder(VoucherOrderRequest request);

    VoucherOrderRequest convertToVoucherOrderRequest(VoucherOrderResponse response);

    VoucherOrderResponse convertToVoucherOrderResponse(VoucherOrder voucherOrder);

//    Page<VoucherOrderResponse> searchVouchers(Pageable pageable, String searchKeyword);

}
