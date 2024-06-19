package com.backend.service.impl;

import com.backend.dto.request.color.ColorRequest;
import com.backend.dto.request.color.ColorRequestUpdate;
import com.backend.dto.response.shoeDetail.ColorResponse;
import com.backend.entity.Color;
import com.backend.repository.ColorRepository;
import com.backend.service.IColorService;
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
public class ColorServiceImpl implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ColorResponse> getAll() {
        List<Color> colorList = colorRepository.findAll();
        return convertToRes(colorList);
    }

    @Override
    public ColorResponse getById(Long id) {
        Color color = colorRepository.findById(id).get();
        return modelMapper.map(color, ColorResponse.class);
    }

    @Override
    public Page<ColorResponse> pageColor(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Color> colorPage = colorRepository.findAll(pageable);
        return colorPage.map(color -> modelMapper.map(color, ColorResponse.class));
    }


    @Override
    public String addColor(ColorRequest colorRequest) {
        Optional<Color> colorOptional = colorRepository.findByNameColor(colorRequest.getName());
        if (colorOptional.isPresent()) {
            if (colorOptional.get().getStatus() == 0) {
                Color color = colorOptional.get();
                color.setStatus(1);
                Color colorUpdate = colorRepository.save(color);
                return "Color updated successfully!";
            } else {
                return "Color already exits!";
            }
        } else {
            if (colorRequest.getName() == null || colorRequest.getName().trim().isEmpty()) {
                return "The name of color not valid!";
            } else {
                Color color = new Color();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                color.setName(colorRequest.getName());
                color.setStatus(1);
                color.setCreatedAt(date);
                color.setUpdatedAt(date);
                colorRepository.save(color);
                return "Category";
            }

        }
    }

    @Override
    public String updateColor(ColorRequestUpdate colorRequestUpdate) {
        Optional<Color> colorOptional = colorRepository.findById(colorRequestUpdate.getId());
        if (colorOptional.isPresent()) {
            if (colorRequestUpdate.getName() == null || (colorRequestUpdate.getName() != null && colorRequestUpdate.getName().trim().isEmpty())) {
                return "The name of color not valid!";
            } else {
                Color colorExits = colorOptional.get();
                colorExits.setId(colorExits.getId());
                colorExits.setName(colorRequestUpdate.getName());
                colorExits.setCreatedAt(colorExits.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                colorExits.setUpdatedAt(calendar.getTime());

                colorExits.setStatus(colorRequestUpdate.getStatus());
                Color colorUpdate = colorRepository.save(colorExits);
                return "The color update succesfully!";
            }

        } else {
            return "The color not found!";
        }
    }

    @Override
    public String deleteColor(Long id) {
        Optional<Color> colorOptional = colorRepository.findById(id);
        if (colorOptional.isPresent()) {
            Color colorExits = colorOptional.get();
            colorExits.setStatus(0);
            colorRepository.save(colorExits);
            return "The color delete succesfully!";
        } else {
            return "The color not found!";
        }
    }

    @Override
    public String activeColor(ColorRequestUpdate colorRequestUpdate) {
        Optional<Color> colorOptional = colorRepository.findById(colorRequestUpdate.getId());
        if (colorOptional.isPresent()) {
            Color colorExits = colorOptional.get();
            colorExits.setStatus(1);
            colorRepository.save(colorExits);
            return "The color active successfully!";
        } else {
            return "The color not found!";
        }
    }

    @Override
    public Color getColorByName(String name) {
        return new Color();
    }


    private List<ColorResponse> convertToRes(List<Color> colorList) {
        return colorList.stream().map(color ->
                ColorResponse.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .status(color.getStatus())
                        .build()).collect(Collectors.toList());
    }
}

