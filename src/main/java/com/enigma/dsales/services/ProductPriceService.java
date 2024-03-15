package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.ProductPrice;

import java.util.Optional;

public interface ProductPriceService {
    ProductPrice create(ProductPrice productPrice);

    Optional<ProductPrice> getById(String id);
    void deleteById(String id);
    //    ProductDetail update(ProductDetail newProductDetail);
    ProductPrice update(ProductPrice newPrice);
}
