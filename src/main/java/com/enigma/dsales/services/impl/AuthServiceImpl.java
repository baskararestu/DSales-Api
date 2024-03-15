package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.config.security.JwtUtil;
import com.enigma.inisalesapi.constant.ERole;
import com.enigma.inisalesapi.dto.request.AuthRequest;
import com.enigma.inisalesapi.dto.request.LoginRequest;
import com.enigma.inisalesapi.dto.response.LoginResponse;
import com.enigma.inisalesapi.dto.response.RegisterResponse;
import com.enigma.inisalesapi.entity.*;
import com.enigma.inisalesapi.mapper.AuthMapper;
import com.enigma.inisalesapi.repository.UserCredentialRepository;
import com.enigma.inisalesapi.service.AdminService;
import com.enigma.inisalesapi.service.AuthService;
import com.enigma.inisalesapi.service.CustomerService;
import com.enigma.inisalesapi.service.RoleService;
import com.enigma.inisalesapi.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        validationUtil.validate(loginRequest);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername().toLowerCase(),
                        loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return AuthMapper.getLoginResponse(loginRequest, token, appUser);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest authRequest) {
        try {
            Role role = AuthMapper.getRole(ERole.ROLE_ADMIN);
            role = roleService.getOrSave(role);

            UserCredential userCredential = AuthMapper.getUserCredential(authRequest, role, passwordEncoder);
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = AuthMapper.getAdmin(authRequest, userCredential);
            adminService.create(admin);

            return AuthMapper.getRegisterResponse(authRequest, userCredential);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user admin already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerSuperAdmin(AuthRequest authRequest) {
        String message = "";
        try {
            Role role = AuthMapper.getRole(ERole.ROLE_SUPER_ADMIN);
            role = roleService.getOrSave(role);

            UserCredential userCredential = AuthMapper.getUserCredential(authRequest, role, passwordEncoder);
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = AuthMapper.getAdmin(authRequest, userCredential);
            adminService.create(admin);
            return AuthMapper.getRegisterResponse(authRequest, userCredential);
        } catch (Exception e) {
            message="admin with role super admin already exist";
            throw new ResponseStatusException(HttpStatus.CONFLICT, message);
        }
    }

    @Override
    @Transactional
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
            Role role = AuthMapper.getRole(ERole.ROLE_CUSTOMER);
            role = roleService.getOrSave(role);

            UserCredential userCredential = AuthMapper.getUserCredential(authRequest, role, passwordEncoder);
            userCredentialRepository.saveAndFlush(userCredential);

            Customer customer = Customer.builder()
                    .name(authRequest.getName())
                    .address(authRequest.getAddress())
                    .email(authRequest.getEmail())
                    .phoneNumber(authRequest.getMobilePhone())
                    .createdAt(LocalDateTime.now())
                    .userCredential(userCredential)
                    .build();
            customerService.create(customer);
            return AuthMapper.getRegisterResponse(authRequest, userCredential);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "customer already exist");
        }
    }
}
