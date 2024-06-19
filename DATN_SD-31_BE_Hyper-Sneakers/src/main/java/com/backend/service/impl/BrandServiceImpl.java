package com.backend.service.impl;

import com.backend.dto.request.brand.BrandRequest;
import com.backend.dto.request.brand.BrandRequestUpdate;
import com.backend.dto.response.BrandResponse;
import com.backend.entity.Brand;
import com.backend.repository.BrandRepository;
import com.backend.service.IBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BrandResponse> getAll() {
        List<Brand> brandList = brandRepository.findAll();
        return convertToRes(brandList);
    }

    @Override
    public Page<BrandResponse> pageBrand(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        return brandPage.map(brand -> modelMapper.map(brand, BrandResponse.class));
    }

    @Override
    public BrandResponse getOne(Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        return modelMapper.map(brand, BrandResponse.class);
    }

    @Override
    public String addNewBrand(BrandRequest brandRequest) {
        Optional<Brand> brandOptional = brandRepository.findByNameBrand(brandRequest.getName());
        if (brandOptional.isPresent()) {
            if (brandOptional.get().getStatus() == 0) {
                Brand brand = brandOptional.get();
                brand.setStatus(1);
                brandRepository.save(brand);
                return "Brand created succesfully!";
            } else {
                return "Category already exits!";
            }
        } else {
            if (brandRequest.getName() == null || brandRequest.getName().trim().isEmpty()) {
                return "The name of brand not valid!";
            } else {
                Brand brand = new Brand();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                brand.setName(brandRequest.getName());
                brand.setStatus(1);
                brand.setCreatedAt(date);
                brand.setUpdatedAt(date);
                brandRepository.save(brand);
                return "Brand created succesfully!";
            }

        }
    }

    @Override
    public String updateBrand(BrandRequestUpdate brandRequestUpdate) {
        Optional<Brand> brandOptional = brandRepository.findById(brandRequestUpdate.getId());
        if (brandOptional.isPresent()) {
            if (brandRequestUpdate.getName() == null || brandRequestUpdate.getName().trim().isEmpty()) {
                return "The name of brand not valid!";
            } else {
                Brand brandExits = brandOptional.get();
                brandExits.setId(brandExits.getId());
                brandExits.setName(brandRequestUpdate.getName());
                brandExits.setCreatedAt(brandExits.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                brandExits.setUpdatedAt(calendar.getTime());

                brandExits.setStatus(brandRequestUpdate.getStatus());
                brandRepository.save(brandExits);
                return "The brand update successfully!";
            }

        } else {
            return "The brand not found!";
        }

    }

    @Override
    public String deleteBrand(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brandExits = brandOptional.get();
            brandExits.setStatus(0);
            Calendar calendar = Calendar.getInstance();
            brandExits.setUpdatedAt(calendar.getTime());
            brandRepository.save(brandExits);
            return "The brand delete succesfully!";
        } else {
            return "The brand not found!";
        }
    }

    @Override
    public String activeBrand(BrandRequestUpdate brandRequestUpdate) {
        Optional<Brand> brandOptional = brandRepository.findById(brandRequestUpdate.getId());
        if (brandOptional.isPresent()) {
            Brand brandExits = brandOptional.get();
            brandExits.setStatus(1);
            brandRepository.save(brandExits);
            return "The brand active succesfully!";
        } else {
            return "The brand not found!";
        }
    }

    private List<BrandResponse> convertToRes(List<Brand> brandList) {
        return brandList.stream().map(brand ->
                BrandResponse.builder()
                        .id(brand.getId())
                        .name(brand.getName())
                        .status(brand.getStatus())
                        .build()).collect(Collectors.toList());
    }
}
