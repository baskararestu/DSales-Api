package com.enigma.inisalesapi.mapper;

import com.enigma.inisalesapi.constant.ERole;
import com.enigma.inisalesapi.dto.request.AuthRequest;
import com.enigma.inisalesapi.dto.request.LoginRequest;
import com.enigma.inisalesapi.dto.response.LoginResponse;
import com.enigma.inisalesapi.dto.response.RegisterResponse;
import com.enigma.inisalesapi.entity.Admin;
import com.enigma.inisalesapi.entity.AppUser;
import com.enigma.inisalesapi.entity.Role;
import com.enigma.inisalesapi.entity.UserCredential;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthMapper {
    public static Role getRole(ERole eRole) {
        return Role.builder()
                .roleName(eRole)
                .build();
    }
    public static UserCredential getUserCredential
            (AuthRequest authRequest, Role role, PasswordEncoder passwordEncoder) {
        return UserCredential.builder()
                .username(authRequest.getUsername())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(role)
                .build();
    }


    public static Admin getAdmin(AuthRequest authRequest, UserCredential userCredential) {
        return Admin.builder()
                .userCredential(userCredential)
                .name(authRequest.getName())
                .email(authRequest.getEmail())
                .phoneNumber(authRequest.getMobilePhone())
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
