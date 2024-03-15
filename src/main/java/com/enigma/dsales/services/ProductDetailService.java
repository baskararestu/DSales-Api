package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.ProductDetail;

import java.util.Optional;

public interface ProductDetailService {
    ProductDetail createProductDetail(ProductDetail productDetail);
    Optional<ProductDetail> getById(String id);
    void deleteById(String id);
    ProductDetail update(ProductDetail newProductDetail);

    ProductDetail findByName(String productName);
}
