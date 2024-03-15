package com.enigma.dsales.services;


import com.enigma.dsales.dto.request.CategoryRequest;
import com.enigma.dsales.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    Page<CategoryResponse>getAllByName(String name, Integer page, Integer size);
    CategoryResponse getCategoryById(String id);

    CategoryResponse getCategoryByName(String name);
    CategoryResponse updateCategoryById(String id,CategoryRequest categoryRequest);
    void deleteCategoryById(String id);
}
