package com.enigma.dsales.services;


import com.enigma.dsales.entities.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
