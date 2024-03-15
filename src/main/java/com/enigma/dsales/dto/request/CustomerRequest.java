package com.enigma.dsales.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerRequest {
    private String id;
    private String name;
    private String email;
    private String address;
    private String mobilePhone;
    private String password;
}
