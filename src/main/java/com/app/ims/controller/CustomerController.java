package com.app.ims.controller;

import com.app.ims.dto.UpdateCustomerRequest;
import com.app.ims.model.Customer;
import com.app.ims.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@NotNull @PathVariable Long id){
        LOGGER.debug("getCustomerById : {}",id);
        return new ResponseEntity<>(customerRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@NotBlank @PathVariable String email){
        LOGGER.debug("getUserByEmail : {}",email);
        return new ResponseEntity<>(customerRepository.findByEmail(email).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/contact/{contact}")
    public ResponseEntity<Customer> getCustomerByContact(@NotBlank @PathVariable String contact){
        LOGGER.debug("getCustomerByContact : {}",contact);
        return new ResponseEntity<>(customerRepository.findByContact(contact).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomerById(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest customerRequest){
        LOGGER.debug("updateCustomerById id : {} and customerRequest : {}", id, customerRequest.toString());
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id : " + id));
        customer.setName(customerRequest.getName());
        customer.setContact(customerRequest.getContact());
        customer.setEmail(customerRequest.getEmail());
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomerById(@NotNull @PathVariable Long id){
        LOGGER.debug("deleteCustomerById id : {}", id);
        customerRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

}
