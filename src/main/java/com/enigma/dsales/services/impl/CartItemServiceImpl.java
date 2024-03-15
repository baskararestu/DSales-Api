package com.enigma.dsales.services.impl;

import com.enigma.dsales.config.security.JwtUtil;
import com.enigma.dsales.dto.request.CartItemRequest;
import com.enigma.dsales.dto.response.CartItemResponse;
import com.enigma.dsales.dto.response.CustomerResponse;
import com.enigma.dsales.dto.response.ProductResponse;
import com.enigma.dsales.entities.CartItem;
import com.enigma.dsales.entities.Customer;
import com.enigma.dsales.entities.Product;
import com.enigma.dsales.repository.CartItemRepository;
import com.enigma.dsales.services.CartItemService;
import com.enigma.dsales.services.CustomerService;
import com.enigma.dsales.services.ProductService;
import com.enigma.dsales.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final ValidationUtil util;
    private final JwtUtil jwtUtil;
    private final CustomerService customerService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CartItemResponse addItem(CartItemRequest request) {
        String token = util.extractTokenFromHeader();

        String customerId = jwtUtil.getUserInfoByToken(token).get("customerId");
        CustomerResponse dataCustomer = customerService.getById(customerId);
        if(dataCustomer==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found!");
        }
        ProductResponse dataProduct = productService.getById(request.getProductId());
        if(dataProduct == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found!");
        }
        Integer stockProduct = dataProduct.getStock();
        if(stockProduct<request.getQuantity()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"product stock is insufficient");
        }
        if(request.getQuantity().equals(0)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Quantity cannot be 0");
        }

     cartItemRepository.addItem
              (request.getQuantity(),customerId,request.getProductId());
        return CartItemResponse.builder()
                .productName(dataProduct.getProductName())
                .categoryName(dataProduct.getProductCategory().getCategoryName())
                .price(dataProduct.getPrice().toString())
                .quantity(request.getQuantity())
                .build();
    }
}
