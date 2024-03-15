package com.enigma.dsales.services;


import com.enigma.dsales.dto.response.CustomerResponse;
import com.enigma.dsales.entities.Customer;

public interface CustomerService {
    CustomerResponse create(Customer customer);
    CustomerResponse getById(String id);
}
