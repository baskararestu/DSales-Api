package com.enigma.inisalesapi.controller;

import com.enigma.inisalesapi.constant.AppPath;
import com.enigma.inisalesapi.dto.response.AdminResponse;
import com.enigma.inisalesapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ADMIN)
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/{id}")
    public AdminResponse getAdminById(@PathVariable String id) {
        return adminService.getById(id);
    }
}
