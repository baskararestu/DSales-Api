package com.enigma.dsales.services;


import com.enigma.dsales.dto.response.CustomerResponse;
import com.enigma.dsales.entities.Customer;

import java.util.Optional;

public interface CustomerService {
    CustomerResponse create(Customer customer);
    CustomerResponse getById(String id);
    Optional<Customer> getByUserCredentialId(String userCredentialId);
}
