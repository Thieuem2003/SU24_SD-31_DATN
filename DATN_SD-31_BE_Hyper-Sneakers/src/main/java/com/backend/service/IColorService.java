package com.backend.service;

import com.backend.dto.request.color.ColorRequest;
import com.backend.dto.request.color.ColorRequestUpdate;
import com.backend.dto.response.shoeDetail.ColorResponse;
import com.backend.entity.Color;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IColorService {

    List<ColorResponse> getAll();

    ColorResponse getById(Long id);

    Page<ColorResponse> pageColor(Integer page, Integer size);

    String addColor(ColorRequest colorRequest);

    String updateColor(ColorRequestUpdate colorRequestUpdate);

    String deleteColor(Long id);

    String activeColor(ColorRequestUpdate colorRequestUpdate);

    Color getColorByName(String name);
}
