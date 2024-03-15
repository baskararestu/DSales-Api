package com.enigma.dsales.services.impl;

import com.enigma.dsales.entities.AppUser;
import com.enigma.dsales.entities.UserCredential;
import com.enigma.dsales.repository.UserCredentialRepository;
import com.enigma.dsales.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.enigma.dsales.mapper.AppUserMapper.mapToAppUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return mapToAppUser(userCredential);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return mapToAppUser(userCredential);
    }
}
