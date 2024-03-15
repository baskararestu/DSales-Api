package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.entity.ProductPrice;
import com.enigma.inisalesapi.exception.NotFoundException;
import com.enigma.inisalesapi.repository.ProductPriceRepository;
import com.enigma.inisalesapi.service.ProductPriceService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    private final EntityManager entityManager;
    @Override
    @Transactional
    public ProductPrice create(ProductPrice productPrice) {
        String priceId=UUID.randomUUID().toString();
        productPriceRepository.insertProductPriceNative
                (priceId,true,productPrice.getPrice(),productPrice.getStock(),LocalDateTime.now());
        Optional<ProductPrice> getCreatedPrice=getById(priceId);
        ProductPrice getPrice = getCreatedPrice.get();
        return ProductPrice.builder()
                .id(getPrice.getId())
                .price(getPrice.getPrice())
                .stock(getPrice.getStock())
                .createdAt(getPrice.getCreatedAt())
                .isActive(getPrice.isActive())
                .build();
    }

    @Override
    public Optional<ProductPrice> getById(String id) {
        return productPriceRepository.findProductPricesByIdNative(id);
    }

    @Override
    public void deleteById(String id){
        Optional<ProductPrice> productPrice = productPriceRepository.findProductPricesByIdNative(id);
        productPrice.ifPresent(p->{
            productPriceRepository.softDeleteProduct(p.getId());
        });
    }

    @Override
    public ProductPrice update(ProductPrice newPrice) {
        ProductPrice existingPrice = productPriceRepository.findById(newPrice.getId()).orElseThrow(() -> new NotFoundException("Product Detail not found."));
        if(!existingPrice.equals(newPrice)){
            existingPrice.setActive(false);
            productPriceRepository.save(existingPrice);

            ProductPrice savedPrice = ProductPrice.builder()
                    .id(newPrice.getId())
                    .price(newPrice.getPrice())
                    .stock(newPrice.getStock())
                    .build();
            return savedPrice;
        }
        return existingPrice;
    }
}
