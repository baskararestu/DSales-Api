package com.enigma.dsales.services.impl;

import com.enigma.dsales.dto.response.AdminResponse;
import com.enigma.dsales.entities.Admin;
import com.enigma.dsales.repository.AdminRepository;
import com.enigma.dsales.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public AdminResponse create(Admin admin) {
         adminRepository.insertAdminNative(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                LocalDateTime.now(),
                 admin.getUserCredential().getId()

         );
        String generatedId = adminRepository.findLastInsertedId();

        AdminResponse savedAdmin = getById(generatedId);

        return AdminResponse.builder()
                .name(savedAdmin.getName())
                .email(savedAdmin.getEmail())
                .phoneNumber(savedAdmin.getPhoneNumber())
                .createdAt(savedAdmin.getCreatedAt())
                .build();
    }

    @Override
    public AdminResponse getById(String id) {
        Admin admin = adminRepository.findByIdNative(id);

            return AdminResponse.builder()
                    .id(admin.getId())
                    .name(admin.getName())
                    .email(admin.getEmail())
                    .phoneNumber(admin.getPhoneNumber())
                    .createdAt(admin.getCreatedAt())
                    .build();
    }
}
