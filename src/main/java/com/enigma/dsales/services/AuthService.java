package com.enigma.inisalesapi.service;


import com.enigma.inisalesapi.dto.request.AuthRequest;
import com.enigma.inisalesapi.dto.request.LoginRequest;
import com.enigma.inisalesapi.dto.response.LoginResponse;
import com.enigma.inisalesapi.dto.response.RegisterResponse;

public interface AuthService {
    LoginResponse login (LoginRequest loginRequest);
    RegisterResponse registerAdmin(AuthRequest authRequest);
    RegisterResponse registerSuperAdmin(AuthRequest authRequest);

    RegisterResponse registerCustomer(AuthRequest authRequest);

}
