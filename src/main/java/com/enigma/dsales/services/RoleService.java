package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
