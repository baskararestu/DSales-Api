package com.enigma.dsales.services.impl;

import com.enigma.dsales.config.security.JwtUtil;
import com.enigma.dsales.constant.ERole;
import com.enigma.dsales.dto.request.AuthRequest;
import com.enigma.dsales.dto.request.LoginRequest;
import com.enigma.dsales.dto.response.LoginResponse;
import com.enigma.dsales.dto.response.RegisterResponse;
import com.enigma.dsales.entities.*;
import com.enigma.dsales.mapper.AuthMapper;
import com.enigma.dsales.repository.UserCredentialRepository;
import com.enigma.dsales.services.AdminService;
import com.enigma.dsales.services.AuthService;
import com.enigma.dsales.services.CustomerService;
import com.enigma.dsales.services.RoleService;
import com.enigma.dsales.util.ValidationUtil;
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

import static com.enigma.dsales.mapper.AdminMapper.mapToAdmin;
import static com.enigma.dsales.mapper.RoleMapper.mapToRole;

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
            Role role = mapToRole(ERole.ROLE_ADMIN);
            role = roleService.getOrSave(role);

            UserCredential userCredential = AuthMapper.getUserCredential(authRequest, role, passwordEncoder);
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = mapToAdmin(authRequest, userCredential);
            adminService.create(admin);

            return AuthMapper.getRegisterResponse(authRequest, userCredential);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user admin already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerSuperAdmin(AuthRequest authRequest) {
        String message;
        try {
            Role role = mapToRole(ERole.ROLE_SUPER_ADMIN);
            role = roleService.getOrSave(role);

            UserCredential userCredential = AuthMapper.getUserCredential(authRequest, role, passwordEncoder);
            userCredentialRepository.saveAndFlush(userCredential);

            Admin admin = mapToAdmin(authRequest, userCredential);
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
            Role role = mapToRole(ERole.ROLE_CUSTOMER);
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
