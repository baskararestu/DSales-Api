package com.enigma.dsales.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String address;
    private String mobilePhone;
    private LocalDateTime createdAt;
}
