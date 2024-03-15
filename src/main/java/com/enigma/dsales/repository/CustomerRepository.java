package com.enigma.dsales.repository;

import com.enigma.dsales.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    Optional<Customer>findCustomerByUserCredentialId(String user_credential_id);
}
