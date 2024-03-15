package com.enigma.dsales.mapper;

import com.enigma.dsales.dto.request.AuthRequest;
import com.enigma.dsales.entities.Admin;
import com.enigma.dsales.entities.UserCredential;

public class AdminMapper {
    public static Admin mapToAdmin(AuthRequest authRequest, UserCredential userCredential) {
        return Admin.builder()
                .userCredential(userCredential)
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .phoneNumber(authRequest.getMobilePhone())
                .build();
    }
}
