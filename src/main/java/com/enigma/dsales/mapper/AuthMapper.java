package com.enigma.dsales.mapper;

import com.enigma.dsales.constant.ERole;
import com.enigma.dsales.dto.request.AuthRequest;
import com.enigma.dsales.dto.request.LoginRequest;
import com.enigma.dsales.dto.response.LoginResponse;
import com.enigma.dsales.dto.response.RegisterResponse;
import com.enigma.dsales.entities.Admin;
import com.enigma.dsales.entities.AppUser;
import com.enigma.dsales.entities.Role;
import com.enigma.dsales.entities.UserCredential;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthMapper {
    public static UserCredential getUserCredential
            (AuthRequest authRequest, Role role, PasswordEncoder passwordEncoder) {
        return UserCredential.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(role)
                .build();
    }



    public static RegisterResponse getRegisterResponse(AuthRequest authRequest, UserCredential userCredential) {
        return RegisterResponse.builder()
                .username(userCredential.getUsername())
                .name(authRequest.getName())
                .role(userCredential.getRole().getRoleName().toString())
                .build();
    }
    public static LoginResponse getLoginResponse(LoginRequest loginRequest, String token, AppUser appUser) {
        return LoginResponse.builder()
                .username(loginRequest.getUsername())
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
