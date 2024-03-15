package com.enigma.inisalesapi.service.impl;

import com.enigma.inisalesapi.dto.response.CustomerResponse;
import com.enigma.inisalesapi.entity.Customer;
import com.enigma.inisalesapi.repository.CustomerRepository;
import com.enigma.inisalesapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse create(Customer customer) {
        customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .mobilePhone(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    @Override
    public CustomerResponse getById(String id) {
       Customer customer = customerRepository.findById(id).orElse(null);
        if(customer != null){
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .mobilePhone(customer.getPhoneNumber())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .build();
        }
        return null;
    }
}
