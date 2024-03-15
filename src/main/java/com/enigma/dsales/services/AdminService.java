package com.enigma.dsales.services;


import com.enigma.dsales.dto.response.AdminResponse;
import com.enigma.dsales.entities.Admin;

public interface AdminService {
    AdminResponse create(Admin admin);

    AdminResponse getById(String id);
}
