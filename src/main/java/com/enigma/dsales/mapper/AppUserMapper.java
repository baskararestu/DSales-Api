package com.enigma.dsales.mapper;

import com.enigma.dsales.entities.AppUser;
import com.enigma.dsales.entities.UserCredential;

public class AppUserMapper {
    public static AppUser mapToAppUser(UserCredential userCredential) {
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getRoleName())
                .build();
    }
}
