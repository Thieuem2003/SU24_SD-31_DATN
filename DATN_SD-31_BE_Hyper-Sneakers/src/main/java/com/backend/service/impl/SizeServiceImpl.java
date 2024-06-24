package com.backend.service.impl;

import com.backend.dto.request.size.SizeRequest;
import com.backend.dto.request.size.SizeRequestUpdate;
import com.backend.dto.response.shoeDetail.SizeRespose;
import com.backend.entity.Size;
import com.backend.repository.SizeRepository;
import com.backend.service.ISizeService;
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
public class SizeServiceImpl implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SizeRespose> getAll() {
        List<Size> sizeList = sizeRepository.findAll();
        return convertToRes(sizeList);
    }

    @Override
    public SizeRespose getOne(Long id) {
        Size size = sizeRepository.findById(id).get();
        return modelMapper.map(size, SizeRespose.class);
    }

    @Override
    public Page<SizeRespose> pageSize(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Size> pageSize = sizeRepository.findAll(pageable);
        return pageSize.map(sizex -> modelMapper.map(sizex, SizeRespose.class));
    }

    @Override
    public String addSize(SizeRequest sizeRequest) {
        Optional<Size> sizeOptional = sizeRepository.findByNameSize(sizeRequest.getName());
        if (sizeOptional.isPresent()) {
            if (sizeOptional.get().getStatus() == 0) {
                Size size = sizeOptional.get();
                size.setStatus(1);
                Size sizeUpdate = sizeRepository.save(size);
                return "Size updated successfully!";
            } else {
                return "Size already exits!";
            }
        } else {
            if (sizeRequest.getName() == null) {
                return "The name of size not valid!";
            } else {
                Size size = new Size();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                size.setName(sizeRequest.getName());
                size.setStatus(1);
                size.setCreatedAt(date);
                size.setUpdatedAt(date);
                sizeRepository.save(size);
                return "Category";
            }
        }
    }

    @Override
    public String updateSize(SizeRequestUpdate sizeRequestUpdate) {
        Optional<Size> sizeOptional = sizeRepository.findById(sizeRequestUpdate.getId());
        if (sizeOptional.isPresent()) {
            if (sizeRequestUpdate.getName() == null) {
                return "The name of size not valid!";
            } else {
                Size sizeExits = sizeOptional.get();
                sizeExits.setId(sizeExits.getId());
                sizeExits.setName(sizeRequestUpdate.getName());
                sizeExits.setCreatedAt(sizeExits.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                sizeExits.setUpdatedAt(calendar.getTime());

                sizeExits.setStatus(sizeRequestUpdate.getStatus());
                Size sizeUpdate = sizeRepository.save(sizeExits);
                return "The size update succesfully!";
            }
        } else {
            return "The size not found!";
        }
    }

    @Override
    public String deleteSize(Long id) {
        Optional<Size> sizeOptional = sizeRepository.findById(id);
        if (sizeOptional.isPresent()) {
            Size sizeExits = sizeOptional.get();
            sizeExits.setStatus(0);
            sizeRepository.save(sizeExits);
            return "The size delete succesfully!";
        } else {
            return "The size not found!";
        }
    }

    @Override
    public String activeSize(SizeRequestUpdate sizeRequestUpdate) {
        Optional<Size> sizeOptional = sizeRepository.findById(sizeRequestUpdate.getId());
        if (sizeOptional.isPresent()) {
            Size sizeExits = sizeOptional.get();
            sizeExits.setStatus(1);
            sizeRepository.save(sizeExits);
            return "The size active succesfully!";
        } else {
            return "The size not found!";
        }
    }


    private List<SizeRespose> convertToRes(List<Size> sizeList) {
        return sizeList.stream().map(size ->
                SizeRespose.builder()
                        .id(size.getId())
                        .name(size.getName())
                        .status(size.getStatus())
                        .build()).collect(Collectors.toList());
    }
}
