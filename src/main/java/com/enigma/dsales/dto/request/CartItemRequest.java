package com.enigma.dsales.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CartItemRequest {
    @NotBlank
    private Integer quantity;
    @NotBlank
    private String productId;
}
