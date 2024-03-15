package com.enigma.dsales.services;

import com.enigma.dsales.dto.request.ProductRequest;
import com.enigma.dsales.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getById(String id);
    Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size);
    void delete(String id);
    ProductResponse updateProduct(String productId,ProductRequest productRequest);
    boolean getByNameAndCategory(String productName,String productCategory);
}
