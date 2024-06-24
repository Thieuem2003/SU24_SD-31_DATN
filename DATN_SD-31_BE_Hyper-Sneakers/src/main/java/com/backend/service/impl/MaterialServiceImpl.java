package com.backend.service.impl;

import com.backend.dto.request.material.MaterialRequest;
import com.backend.dto.request.material.MaterialRequestUpdate;
import com.backend.dto.response.MaterialResponse;
import com.backend.entity.Material;
import com.backend.repository.MaterialRepository;
import com.backend.service.IMaterialService;
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
public class MaterialServiceImpl implements IMaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MaterialResponse> getAll() {
        List<Material> materialList = materialRepository.findAll();
        return convertToRes(materialList);
    }

    @Override
    public MaterialResponse getOne(Long id) {
        Material material = materialRepository.findById(id).orElse(null);
        return modelMapper.map(material, MaterialResponse.class);
    }

    @Override
    public Page<MaterialResponse> pageMaterial(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Material> materialPage = materialRepository.findAll(pageable);
        return materialPage.map(material -> modelMapper.map(material, MaterialResponse.class));
    }

    @Override
    public String addNewMaterial(MaterialRequest materialRequest) {
        Optional<Material> materialOptional = materialRepository.findByNameMaterial(materialRequest.getName());
        if (materialOptional.isPresent()) {
            if (materialOptional.get().getStatus() == 0) {
                Material material = materialOptional.get();
                material.setStatus(1);
                materialRepository.save(material);
                return "Material updated succesfully!";
            } else {
                return "Material already exits!";
            }
        } else {
            if (materialRequest.getName() == null || materialRequest.getName().trim().isEmpty()) {
                return "The name of material not valid!";
            } else {
                Material material = new Material();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                material.setName(materialRequest.getName());
                material.setStatus(1);
                material.setCreatedAt(date);
                material.setUpdatedAt(date);
                materialRepository.save(material);
                return "Material added succesfully!";
            }

        }
    }

    @Override
    public String updateMaterial(MaterialRequestUpdate materialRequestUpdate) {
        Optional<Material> materialOptional = materialRepository.findById(materialRequestUpdate.getId());
        if (materialOptional.isPresent()) {
            if (materialRequestUpdate.getName() == null || materialRequestUpdate.getName().trim().isEmpty()) {
                return "The name of material not valid!";
            } else {
                Material material = materialOptional.get();
                material.setId(materialRequestUpdate.getId());
                material.setName(materialRequestUpdate.getName());
                material.setCreatedAt(materialRequestUpdate.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                material.setUpdatedAt(calendar.getTime());

                material.setStatus(materialRequestUpdate.getStatus());
                materialRepository.save(material);
                return "The material update succesfully!";
            }

        } else {
            return "The material not found!";
        }
    }

    @Override
    public String deleteMaterial(Long id) {
        Optional<Material> materialOptional = materialRepository.findById(id);
        if (materialOptional.isPresent()) {
            Material material = materialOptional.get();
            material.setStatus(0);
            materialRepository.save(material);
            return "The material delete succesfully!";
        } else {
            return "The material not found!";
        }
    }

    @Override
    public String activeMaterial(MaterialRequestUpdate materialRequestUpdate) {
        Optional<Material> materialOptional = materialRepository.findById(materialRequestUpdate.getId());
        if (materialOptional.isPresent()) {
            Material material = materialOptional.get();
            material.setStatus(1);
            materialRepository.save(material);
            return "The material active succesfully!";
        } else {
            return "The material not found!";
        }
    }

    private List<MaterialResponse> convertToRes(List<Material> materialList) {
        return materialList.stream().map(color ->
                MaterialResponse.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .status(color.getStatus())
                        .build()).collect(Collectors.toList());
    }
}
