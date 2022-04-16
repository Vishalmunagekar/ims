package com.app.ims.repository;

import com.app.ims.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByContact(String contact);
    Optional<Customer> findByEmail(String email);
}
