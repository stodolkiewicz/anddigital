package com.anddigital.challenge.controller;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.exception.PhoneNumberNotFoundException;
import com.anddigital.challenge.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PhoneController {

    PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService){
        this.phoneService = phoneService;
    }

    public PhoneController() {}

    @GetMapping("/phone/numbers")
    public ResponseEntity<List<?>> getAllPhoneNumbers(){
        List<String> allPhoneNumbers = phoneService.getAllPhoneNumbers();

        if(allPhoneNumbers.size() == 0){
            throw new NoSuchElementException();
        }

        return new ResponseEntity<>(allPhoneNumbers, null, HttpStatus.OK);
    }

    //debugging purposes
    @GetMapping("/phones")
    public List<?> getAllPhones(){
        return phoneService.getAllPhones();
    }

    @PutMapping("/phone/{phoneNumber}/activated/{activationValue}")
    public ResponseEntity<?> activationOfPhoneNumber(@PathVariable String phoneNumber, @PathVariable boolean activationValue){
        try {
            phoneService.setPhoneNumberActivation(phoneNumber, activationValue);
        } catch (PhoneNumberNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        } catch (DuplicatedPhoneNumberException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
