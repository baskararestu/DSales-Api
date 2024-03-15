package com.enigma.dsales.services.impl;

import com.enigma.dsales.entities.Role;
import com.enigma.dsales.repository.RoleRepository;
import com.enigma.dsales.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(role.getRoleName());
        if(!optionalRole.isEmpty()){
            return optionalRole.get();
        }
        return roleRepository.save(role);
    }
}
