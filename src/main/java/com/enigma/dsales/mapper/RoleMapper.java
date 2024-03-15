package com.enigma.dsales.mapper;

import com.enigma.dsales.constant.ERole;
import com.enigma.dsales.entities.Role;

public class RoleMapper {
    public static Role mapToRole(ERole eRole) {
        return Role.builder()
                .roleName(eRole)
                .build();
    }
}
