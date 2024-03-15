package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.entity.Role;
import com.enigma.inisalesapi.repository.RoleRepository;
import com.enigma.inisalesapi.service.RoleService;
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
