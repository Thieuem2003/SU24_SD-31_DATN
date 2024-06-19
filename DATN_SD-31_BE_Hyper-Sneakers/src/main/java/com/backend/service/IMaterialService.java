package com.backend.service;

import com.backend.dto.request.material.MaterialRequest;
import com.backend.dto.request.material.MaterialRequestUpdate;
import com.backend.dto.response.MaterialResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMaterialService {
    List<MaterialResponse> getAll();

    MaterialResponse getOne(Long id);

    Page<MaterialResponse> pageMaterial(Integer page, Integer size);

    String addNewMaterial(MaterialRequest materialRequest);

    String updateMaterial(MaterialRequestUpdate materialRequestUpdate);

    String deleteMaterial(Long id);

    String activeMaterial(MaterialRequestUpdate materialRequestUpdate);
}
