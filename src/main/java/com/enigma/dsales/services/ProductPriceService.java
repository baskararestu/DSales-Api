package com.enigma.dsales.services;

import com.enigma.dsales.entities.ProductPrice;

import java.util.Optional;

public interface ProductPriceService {
    ProductPrice create(ProductPrice productPrice);

    Optional<ProductPrice> getById(String id);
    void deleteById(String id);
    ProductPrice update(ProductPrice newPrice);
}
