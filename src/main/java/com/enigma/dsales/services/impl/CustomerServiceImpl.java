package com.enigma.dsales.services.impl;

import com.enigma.dsales.dto.response.CustomerResponse;
import com.enigma.dsales.entities.Customer;
import com.enigma.dsales.repository.CustomerRepository;
import com.enigma.dsales.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Customer> getByUserCredentialId(String userCredentialId) {
        return customerRepository.findCustomerByUserCredentialId(userCredentialId);
    }
}
