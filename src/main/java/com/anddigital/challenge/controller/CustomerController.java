package com.anddigital.challenge.controller;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer/{customerId}/phoneNumbers")
    public ResponseEntity<?> getCustomerPhoneNumbers(@PathVariable Long customerId){
        try {
            return new ResponseEntity<>(customerService.getCustomerPhoneNumbers(customerId), HttpStatus.OK);
        } catch (DuplicatedPhoneNumberException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
