package com.backend.service;

import com.backend.dto.request.sole.SoleRequest;
import com.backend.dto.request.sole.SoleRequestUpdate;
import com.backend.dto.response.SoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISoleService {
    List<SoleResponse> getAll();

    Page<SoleResponse> pageSole(Integer pageNo, Integer size);

    SoleResponse getOne(Long id);

    String addNewSole(SoleRequest soleRequest);

    String updateSole(SoleRequestUpdate soleRequestUpdate);

    String deleteSole(Long id);

    String activeSole(SoleRequestUpdate soleRequestUpdate);
}
