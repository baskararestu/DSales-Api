package com.enigma.dsales.services.impl;

import com.enigma.dsales.dto.request.CategoryRequest;
import com.enigma.dsales.dto.response.CategoryResponse;
import com.enigma.dsales.entities.Category;
import com.enigma.dsales.repository.CategoryRepository;
import com.enigma.dsales.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryId = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();

        categoryRepository.insertCategoryNative(
                categoryId,
                categoryRequest.getCategoryName(),
                createdAt
        );

        return CategoryResponse.builder()
                .id(categoryId)
                .categoryName(categoryRequest.getCategoryName())
                .createdAt(createdAt)
                .build();
    }


    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findByIdNative(id);
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .createdAt(category.getCreatedAt())
                .build();
    }

    @Override
    public Page<CategoryResponse> getAllByName(String name, Integer page, Integer size) {
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size));
        return categoryPage.map(category -> CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .createdAt(category.getCreatedAt())
                .build());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategoryById(String id, CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = getCategoryById(id);
        if (categoryResponse != null) {
            categoryRepository.updateCategoryNameNative(
                    id,
                    categoryRequest.getCategoryName()
            );

            CategoryResponse updatedCategory = getCategoryById(id);
            if (updatedCategory != null) {

                return CategoryResponse.builder()
                        .id(updatedCategory.getId())
                        .categoryName(updatedCategory.getCategoryName())
                        .createdAt(updatedCategory.getCreatedAt())
                        .build();
            } else {
                throw new RuntimeException("Failed to retrieve updated category.");
            }
        }
        throw new RuntimeException("Category not found with id: " + id);
    }

    @Override
    @Transactional
    public void deleteCategoryById(String id) {
        Category category = categoryRepository.findByIdNative(id);
        if (category != null) {
            categoryRepository.deleteByIdNative(id);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        return categoryRepository.findCategoryByNameNative(name);
    }
}
