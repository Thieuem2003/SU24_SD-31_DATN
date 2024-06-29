package com.backend.service.impl;

import com.backend.dto.request.VoucherOrderRequest;
import com.backend.dto.response.VoucherOrderResponse;
import com.backend.entity.VoucherOrder;
import com.backend.repository.VoucherOrderRepository;
import com.backend.service.IVoucherOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoucherOrderServiceImpl implements IVoucherOrderService {

    @Autowired
    private VoucherOrderRepository voucherOrderRepository;

    @Override
    public Page<VoucherOrderResponse> getAllVouchers(Pageable pageable, String searchKeyword) {
        Page<VoucherOrder> vouchersPage;
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            vouchersPage = voucherOrderRepository.findByNameContainingOrCodeContaining(searchKeyword, searchKeyword, pageable);
        } else {
            vouchersPage = voucherOrderRepository.findAll(pageable);
        }
        return vouchersPage.map(this::convertToVoucherOrderResponse);
    }

    @Override
    public Optional<VoucherOrderResponse> getVoucherById(Long id) {
        return voucherOrderRepository.findById(id).map(this::convertToVoucherOrderResponse);
    }

    @Override
    public VoucherOrder saveVoucher(VoucherOrder voucherOrder) {
        return voucherOrderRepository.save(voucherOrder);
    }

    @Override
    public void deleteVoucher(Long id) {
        voucherOrderRepository.deleteById(id);
    }

    @Override
    public VoucherOrder convertToVoucherOrder(VoucherOrderRequest request) {
        return VoucherOrder.builder()
                .id(request.getId())
                .code(request.getCode())
                .name(request.getName())
                .quantity(request.getQuantity())
                .discountAmount(request.getDiscountAmount())
                .minBillValue(request.getMinBillValue())
                .maximumReductionValue(request.getMaximumReductionValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .createDate(request.getCreateDate())
                .updateAt(request.getUpdateAt())
                .reduceForm(request.getReduceForm())
                .status(request.getStatus())
                .build();
    }

    @Override
    public VoucherOrderRequest convertToVoucherOrderRequest(VoucherOrderResponse response) {
        return VoucherOrderRequest.builder()
                .id(response.getId())
                .code(response.getCode())
                .name(response.getName())
                .quantity(response.getQuantity())
                .discountAmount(response.getDiscountAmount())
                .minBillValue(response.getMinBillValue())
                .maximumReductionValue(response.getMaximumReductionValue())
                .startDate(response.getStartDate())
                .endDate(response.getEndDate())
                .createDate(response.getCreateDate())
                .updateAt(response.getUpdateAt())
                .reduceForm(response.getReduceForm())
                .status(response.getStatus())
                .build();
    }

    @Override
    public VoucherOrderResponse convertToVoucherOrderResponse(VoucherOrder voucherOrder) {
        return VoucherOrderResponse.builder()
                .id(voucherOrder.getId())
                .code(voucherOrder.getCode())
                .name(voucherOrder.getName())
                .quantity(voucherOrder.getQuantity())
                .discountAmount(voucherOrder.getDiscountAmount())
                .minBillValue(voucherOrder.getMinBillValue())
                .maximumReductionValue(voucherOrder.getMaximumReductionValue())
                .startDate(voucherOrder.getStartDate())
                .endDate(voucherOrder.getEndDate())
                .createDate(voucherOrder.getCreateDate())
                .updateAt(voucherOrder.getUpdateAt())
                .reduceForm(voucherOrder.getReduceForm())
                .status(voucherOrder.getStatus())
                .build();
    }

//    @Override
//    public Page<VoucherOrderResponse> searchVouchers(Pageable pageable, String searchKeyword) {
//        return voucherOrderRepository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(pageable, searchKeyword, searchKeyword);
//    }
}
