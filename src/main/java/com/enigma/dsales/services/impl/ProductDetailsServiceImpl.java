package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.entity.Category;
import com.enigma.inisalesapi.entity.ProductDetail;
import com.enigma.inisalesapi.exception.NotFoundException;
import com.enigma.inisalesapi.repository.ProductDetailRepository;
import com.enigma.inisalesapi.service.ProductDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    @Override
    @Transactional
    public ProductDetail createProductDetail(ProductDetail productDetail) {
        String detailId= UUID.randomUUID().toString();
        productDetailRepository.insertProductDetailNative
                (detailId,productDetail.getName(),productDetail.getDescription(),true,
                        LocalDateTime.now(),productDetail.getCategory().getId());
        Optional<ProductDetail> createdDetail = getById(detailId);
        ProductDetail getDetail = createdDetail.get();
        return ProductDetail.builder()
                .id(getDetail.getId())
                .name(getDetail.getName())
                .description(getDetail.getDescription())
                .isActive(getDetail.getIsActive())
                .createdAt(getDetail.getCreatedAt())
                .category(Category.builder()
                        .id(getDetail.getCategory().getId())
                        .build())
                .build();
    }

    @Override
    public Optional<ProductDetail> getById(String id) {
      return   productDetailRepository.findProductDetailByIdNative(id);
    }

    @Override
    public void deleteById(String id){
        Optional<ProductDetail> detail = productDetailRepository.findProductDetailByIdNative(id);
        detail.ifPresent(d->{
            productDetailRepository.softDeleteProduct(d.getId());
        });
    }

    @Override
    @Transactional
    public ProductDetail update(ProductDetail newProductDetail) {
        ProductDetail existingProductDetail = productDetailRepository.findById(newProductDetail.getId())
                .orElseThrow(() -> new NotFoundException("Product Detail not found."));

        if (!existingProductDetail.equals(newProductDetail)) {
            existingProductDetail.setIsActive(false);
            productDetailRepository.save(existingProductDetail);

            ProductDetail savedDetail = ProductDetail.builder()
                    .name(newProductDetail.getName())
                    .description(newProductDetail.getDescription())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .category(newProductDetail.getCategory())
                    .build();

            return productDetailRepository.save(savedDetail);
        }

        return existingProductDetail;
    }

    @Override
    public ProductDetail findByName(String productName) {
        return productDetailRepository.findProductDetailByName(productName);
    }
}
