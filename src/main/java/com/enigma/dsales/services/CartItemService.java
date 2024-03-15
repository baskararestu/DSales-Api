package com.enigma.dsales.services;

import com.enigma.dsales.dto.request.CartItemRequest;
import com.enigma.dsales.dto.response.CartItemResponse;

public interface CartItemService {
    CartItemResponse addItem(CartItemRequest request);
}
