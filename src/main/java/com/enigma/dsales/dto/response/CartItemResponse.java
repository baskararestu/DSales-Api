package com.enigma.dsales.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CartItemResponse {
    private String productName;
    private String price;
    private Integer quantity;
    private String categoryName;
}
