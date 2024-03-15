package com.enigma.dsales.controller;

import com.enigma.dsales.constant.AppPath;
import com.enigma.dsales.dto.request.AuthRequest;
import com.enigma.dsales.dto.request.LoginRequest;
import com.enigma.dsales.dto.response.LoginResponse;
import com.enigma.dsales.dto.response.RegisterResponse;
import com.enigma.dsales.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.enigma.dsales.mapper.ResponseControllerMapper.getResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class AuthController {
    private final AuthService authService;
    private String message;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admins")
    public ResponseEntity<?> createAdminAccount(@RequestBody AuthRequest authRequest) {
        try {
            RegisterResponse registerResponse = authService.registerAdmin(authRequest);
            message = "Successfully create admin account";
            return getResponseEntity(message, HttpStatus.CREATED, registerResponse);
        } catch (ResponseStatusException e) {
            message = e.getReason();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/superadmins")
    public ResponseEntity<?> createSuperAdminAccount(@RequestBody AuthRequest authRequest) {
        try {
            RegisterResponse registerResponse = authService.registerSuperAdmin(authRequest);
            message = "Successfully create super admin account";
            return getResponseEntity(message, HttpStatus.CREATED, registerResponse);
        } catch (ResponseStatusException e) {
            message = e.getReason();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            message = "Successfully login into app";
            return getResponseEntity(message, HttpStatus.OK, loginResponse);
        } catch (Exception e) {
            message = "Username or password are invalid";
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody AuthRequest authRequest) {
        try {
            RegisterResponse registerResponse = authService.registerCustomer(authRequest);
            message = "Successfully create customer account";
            return getResponseEntity(message,HttpStatus.CREATED,registerResponse);
        } catch (ResponseStatusException e) {
            message = e.getReason();
            return getResponseEntity(message, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            message = e.getMessage();
            return getResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
