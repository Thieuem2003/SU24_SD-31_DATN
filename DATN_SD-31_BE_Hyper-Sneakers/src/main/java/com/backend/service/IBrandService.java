package com.backend.service;

import com.backend.dto.request.brand.BrandRequest;
import com.backend.dto.request.brand.BrandRequestUpdate;
import com.backend.dto.response.BrandResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBrandService {

    List<BrandResponse> getAll();

    Page<BrandResponse> pageBrand(Integer pageNo, Integer size);

    BrandResponse getOne(Long id);

    String addNewBrand(BrandRequest brandRequest);

    String updateBrand(BrandRequestUpdate brandRequestUpdate);

    String deleteBrand(Long id);

    String activeBrand(BrandRequestUpdate brandRequestUpdate);

}
