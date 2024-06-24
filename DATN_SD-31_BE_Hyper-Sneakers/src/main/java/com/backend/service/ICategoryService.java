package com.backend.service;

import com.backend.dto.request.category.CategoryRequest;
import com.backend.dto.request.category.CategoryRequestUpdate;
import com.backend.dto.response.shoeDetail.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    Page<CategoryResponse> getByPage(Integer page, Integer size);

    String addCategory(CategoryRequest categoryRequest);

    String updateCategory(CategoryRequestUpdate categoryRequestUpdate);

    String deleteCategory(Long id);

    String activeCategory(CategoryRequestUpdate categoryRequestUpdate);
}
