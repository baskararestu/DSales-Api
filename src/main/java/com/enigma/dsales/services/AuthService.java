package com.enigma.dsales.services;


import com.enigma.dsales.dto.request.AuthRequest;
import com.enigma.dsales.dto.request.LoginRequest;
import com.enigma.dsales.dto.response.LoginResponse;
import com.enigma.dsales.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login (LoginRequest loginRequest);
    RegisterResponse registerAdmin(AuthRequest authRequest);
    RegisterResponse registerSuperAdmin(AuthRequest authRequest);

    RegisterResponse registerCustomer(AuthRequest authRequest);

}
