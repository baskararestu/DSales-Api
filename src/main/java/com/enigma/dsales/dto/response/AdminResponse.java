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
public class AdminResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
