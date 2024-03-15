package com.enigma.inisalesapi.service;


import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.response.AdminResponse;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    Page<CategoryResponse>getAllByName(String name, Integer page, Integer size);
    CategoryResponse getCategoryById(String id);

    CategoryResponse getCategoryByName(String name);
    CategoryResponse updateCategoryById(String id,CategoryRequest categoryRequest);
    void deleteCategoryById(String id);
}
