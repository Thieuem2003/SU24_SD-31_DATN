package com.backend.service;

import com.backend.dto.request.size.SizeRequest;
import com.backend.dto.request.size.SizeRequestUpdate;
import com.backend.dto.response.shoeDetail.SizeRespose;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISizeService {

    List<SizeRespose> getAll();

    SizeRespose getOne(Long id);

    Page<SizeRespose> pageSize(Integer page, Integer size);

    String addSize(SizeRequest sizeRequest);

    String updateSize(SizeRequestUpdate sizeRequestUpdate);

    String deleteSize(Long id);

    String activeSize(SizeRequestUpdate sizeRequestUpdate);
}
