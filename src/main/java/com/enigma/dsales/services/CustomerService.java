package com.enigma.inisalesapi.service;

import com.enigma.inisalesapi.dto.response.CustomerResponse;
import com.enigma.inisalesapi.entity.Customer;

public interface CustomerService {
    //AdminResponse create(Admin admin);
    //
    //    AdminResponse getById(String id);
    CustomerResponse create(Customer customer);
    CustomerResponse getById(String id);
}
