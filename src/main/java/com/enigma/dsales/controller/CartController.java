package com.enigma.dsales.controller;

import com.enigma.dsales.constant.AppPath;
import com.enigma.dsales.dto.request.CartItemRequest;
import com.enigma.dsales.dto.response.CartItemResponse;
import com.enigma.dsales.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.enigma.dsales.mapper.ResponseControllerMapper.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CART)
public class CartController {
    private final CartItemService cartItemService;
    private String message;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemRequest request){
        try {
            CartItemResponse data=cartItemService.addItem(request);
            message = "Successfully add item cart";
            return getResponseEntity(message, HttpStatus.OK,data);
        }catch (ResponseStatusException e){
            message = e.getReason();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
