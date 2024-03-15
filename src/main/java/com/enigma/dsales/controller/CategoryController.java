package com.enigma.inisalesapi.controller;

import com.enigma.inisalesapi.constant.AppPath;
import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.dto.response.PagingResponse;
import com.enigma.inisalesapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.enigma.inisalesapi.mapper.ResponseControllerMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;
    private String message;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
    try {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        message = "Successfully create category";
        return getResponseEntity(message, HttpStatus.CREATED, categoryResponse);
    }catch (Exception e) {
        message = e.getMessage();
        return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
    ) {
        try {
            Page<CategoryResponse> categoryResponses = categoryService.getAllByName(name,page,size);
            PagingResponse pagingResponse = PagingResponse.builder()
                    .currentPage(page)
                    .totalPage(categoryResponses.getTotalPages())
                    .size(size)
                    .build();
            message ="successfully retrieve categories";
            return getResponseEntityPaging(message,HttpStatus.OK,categoryResponses.getContent(),pagingResponse);
        }catch (Exception e){
            message= e.getMessage();
            return getResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        try {
            CategoryResponse categoryResponse = categoryService.getCategoryById(id);
            message = "Successfully retrieve category";
            return getResponseEntity(message, HttpStatus.OK, categoryResponse);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@RequestBody CategoryRequest categoryRequest,@PathVariable String id){
        try {
            CategoryResponse categoryResponse = categoryService.updateCategoryById(id,categoryRequest);
            message = "Successfully update category with id "+id;
            return getResponseEntity(message, HttpStatus.OK,categoryResponse);
        }catch (Exception e){
            message = e.getMessage();
            return getResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable String id){
        try {
            categoryService.deleteCategoryById(id);
            message = "Successfully delete category";
            return getResponseEntity(message, HttpStatus.OK,null);
        }catch (Exception e){
            message = e.getMessage();
            return getResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
}
