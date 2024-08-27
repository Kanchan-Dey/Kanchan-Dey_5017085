package com.app.bookstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bookstoreapi.entity.Customer;
import com.app.bookstoreapi.service.CustomerService;

@RestController
public class RegistrationController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/api/customers/register")
    public void registerCustomer(@RequestBody Customer customer){
        customerService.save(customer);
    }
}
