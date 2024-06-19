package com.backend.service.impl;

import com.backend.dto.request.sole.SoleRequest;
import com.backend.dto.request.sole.SoleRequestUpdate;
import com.backend.dto.response.SoleResponse;
import com.backend.entity.Sole;
import com.backend.repository.SoleRepository;
import com.backend.service.ISoleService;
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
public class SoleServiceImpl implements ISoleService {

    @Autowired
    private SoleRepository soleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SoleResponse> getAll() {
        List<Sole> soleList = soleRepository.findAll();
        return convertToRes(soleList);
    }

    @Override
    public Page<SoleResponse> pageSole(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Sole> solePage = soleRepository.findAll(pageable);
        return solePage.map(sole -> modelMapper.map(sole, SoleResponse.class));
    }

    @Override
    public SoleResponse getOne(Long id) {
        Sole sole = soleRepository.findById(id).orElse(null);
        return modelMapper.map(sole, SoleResponse.class);
    }

    @Override
    public String addNewSole(SoleRequest soleRequest) {
        Optional<Sole> soleOptional = soleRepository.findByNameSole(soleRequest.getName());
        if (soleOptional.isPresent()) {
            if (soleOptional.get().getStatus() == 0) {
                Sole sole = soleOptional.get();
                sole.setStatus(1);
                soleRepository.save(sole);
                return "Sole updated successfully!";
            } else {
                return "Sole already exits!";
            }
        } else {
            if (soleRequest.getName() == null || soleRequest.getName().trim().isEmpty()) {
                return "The name of sole not valid!";
            } else {
                Sole sole = new Sole();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                sole.setName(soleRequest.getName());
                sole.setStatus(1);
                sole.setCreatedAt(date);
                sole.setUpdatedAt(date);
                soleRepository.save(sole);
                return "Category";
            }
        }
    }

    @Override
    public String updateSole(SoleRequestUpdate soleRequestUpdate) {
        Optional<Sole> soleOptional = soleRepository.findById(soleRequestUpdate.getId());
        if (soleOptional.isPresent()) {
            if (soleRequestUpdate.getName() == null || soleRequestUpdate.getName().trim().isEmpty()) {
                return "The name of sole not valid!";
            } else {
                Sole soleExits = soleOptional.get();
                soleExits.setId(soleExits.getId());
                soleExits.setName(soleRequestUpdate.getName());
                soleExits.setCreatedAt(soleExits.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                soleExits.setUpdatedAt(calendar.getTime());

                soleExits.setStatus(soleRequestUpdate.getStatus());
                soleRepository.save(soleExits);
                return "The sole update succesfully!";
            }
        } else {
            return "The sole not found!";
        }
    }

    @Override
    public String deleteSole(Long id) {
        Optional<Sole> soleOptional = soleRepository.findById(id);
        if (soleOptional.isPresent()) {
            Sole soleExits = soleOptional.get();
            soleExits.setStatus(0);
            soleRepository.save(soleExits);
            return "The sole delete succesfully!";
        } else {
            return "The sole not found!";
        }
    }

    @Override
    public String activeSole(SoleRequestUpdate soleRequestUpdate) {
        Optional<Sole> soleOptional = soleRepository.findById(soleRequestUpdate.getId());
        if (soleOptional.isPresent()) {
            Sole soleExits = soleOptional.get();
            soleExits.setStatus(1);
            soleRepository.save(soleExits);
            return "The sole active successfully!";
        } else {
            return "The sole not found!";
        }
    }

    private List<SoleResponse> convertToRes(List<Sole> soleList) {
        return soleList.stream().map(sole ->
                SoleResponse.builder()
                        .id(sole.getId())
                        .name(sole.getName())
                        .status(sole.getStatus())
                        .build()).collect(Collectors.toList());
    }
}
