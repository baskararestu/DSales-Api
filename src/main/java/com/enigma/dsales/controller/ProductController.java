package com.enigma.inisalesapi.controller;

import com.enigma.inisalesapi.constant.AppPath;
import com.enigma.inisalesapi.dto.request.CategoryRequest;
import com.enigma.inisalesapi.dto.request.ProductRequest;
import com.enigma.inisalesapi.dto.response.CategoryResponse;
import com.enigma.inisalesapi.dto.response.PagingResponse;
import com.enigma.inisalesapi.dto.response.ProductResponse;
import com.enigma.inisalesapi.exception.NotFoundException;
import com.enigma.inisalesapi.exception.ProductAlreadyExistsException;
import com.enigma.inisalesapi.exception.ProductInactiveException;
import com.enigma.inisalesapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.enigma.inisalesapi.mapper.ResponseControllerMapper.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PRODUCT)
public class ProductController {
    private final ProductService productService;
    private String message;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductResponse productResponse = productService.createProduct(productRequest);
            message = "Successfully create product";
            return getResponseEntity(message, HttpStatus.CREATED, productResponse);
        } catch (ProductAlreadyExistsException e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = "Failed to create product.";
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public  ResponseEntity<?>getProductById(@PathVariable String id){
        try {
          ProductResponse productResponse = productService.getById(id);
          message = "Successfully get product data using product id";
          return getResponseEntity(message,HttpStatus.OK,productResponse);
        }catch (Exception e){
            message = e.getMessage();
            return getResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{id}/soft-delete")
    public ResponseEntity<?> softDeleteById(@PathVariable String id) {
        try {
            productService.delete(id);
            message = "Successfully soft delete product using product id";
            return getResponseEntity(message, HttpStatus.OK, null);
        } catch (NotFoundException e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.NOT_FOUND, null);
        } catch (ProductInactiveException e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody ProductRequest productRequest){
        try {
          ProductResponse product =  productService.updateProduct(id,productRequest);
            message="Successfully update product data";
            return getResponseEntity(message,HttpStatus.OK,product);
        }catch (ProductAlreadyExistsException e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e){
            message=e.getMessage();
            return getResponseEntity(message,HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllProductPage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
    ){
        try {
            Page<ProductResponse> productResponses = productService.getAllByNameOrPrice(name, maxPrice, page, size);
            message = "Successfully getting data";
            PagingResponse pagingResponse = getPagingResponse(page, size, productResponses);
            return getResponseEntityPaging(message, HttpStatus.OK, productResponses.getContent(), pagingResponse);
        } catch (Exception e) {
            return getResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}

