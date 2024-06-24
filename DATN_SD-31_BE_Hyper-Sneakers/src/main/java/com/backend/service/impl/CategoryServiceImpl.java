package com.backend.service.impl;

import com.backend.dto.request.category.CategoryRequest;
import com.backend.dto.request.category.CategoryRequestUpdate;
import com.backend.dto.response.shoeDetail.CategoryResponse;
import com.backend.entity.Category;
import com.backend.repository.CategoryRepository;
import com.backend.service.ICategoryService;
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
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return convertToRes(categoryList);
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return modelMapper.map(category, CategoryResponse.class);
    }

    @Override
    public Page<CategoryResponse> getByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(category -> modelMapper.map(category, CategoryResponse.class));
    }

    @Override
    public String addCategory(CategoryRequest categoryRequest) {
        Optional<Category> categoryOptional = categoryRepository.findByNameCategory(categoryRequest.getName());
        if (categoryOptional.isPresent()) {
            if (categoryOptional.get().getStatus() == 0) {
                Category category = categoryOptional.get();
                category.setStatus(1);
                categoryRepository.save(category);
                return "Category updated succesfully";
            } else {
                return "Category already exits!";
            }
        } else {
            if (categoryRequest.getName() == null || categoryRequest.getName().trim().isEmpty()) {
                return "The name of category not valid!";
            } else {
                Category category = new Category();
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                category.setName(categoryRequest.getName());
                category.setStatus(1);
                category.setCreatedAt(date);
                category.setUpdatedAt(date);
                categoryRepository.save(category);
                return "Category";
            }
        }
    }

    @Override
    public String updateCategory(CategoryRequestUpdate categoryRequestUpdate) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryRequestUpdate.getId());
        if (categoryOptional.isPresent()) {
            if (categoryRequestUpdate.getName() == null || categoryRequestUpdate.getName().trim().isEmpty()) {
                return "The name of category not valid!";
            } else {
                Category categoryExits = categoryOptional.get();
                categoryExits.setId(categoryExits.getId());
                categoryExits.setName(categoryExits.getName());
                categoryExits.setCreatedAt(categoryExits.getCreatedAt());

                Calendar calendar = Calendar.getInstance();
                categoryExits.setUpdatedAt(calendar.getTime());

                categoryExits.setStatus(categoryRequestUpdate.getStatus());
                Category categoryUpdate = categoryRepository.save(categoryExits);
                return "The category update successfully!";
            }
        } else {
            return "The category not found!";
        }
    }


    @Override
    public String deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category categoryExits = categoryOptional.get();
            categoryExits.setStatus(0);
            categoryRepository.save(categoryExits);
            return "The category update successfully!";
        } else {
            return "The category not found!";
        }
    }

    @Override
    public String activeCategory(CategoryRequestUpdate categoryRequestUpdate) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryRequestUpdate.getId());
        if (categoryOptional.isPresent()) {
            Category categoryExits = categoryOptional.get();
            categoryExits.setStatus(1);
            categoryRepository.save(categoryExits);
            return "The category update successfully!";
        } else {
            return "The category not found!";
        }
    }

    private List<CategoryResponse> convertToRes(List<Category> categoryList) {
        return categoryList.stream().map(category ->
                CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .status(category.getStatus())
                        .build()).collect(Collectors.toList());
    }
}
