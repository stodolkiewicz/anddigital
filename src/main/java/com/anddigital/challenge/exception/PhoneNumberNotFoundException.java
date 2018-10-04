package com.anddigital.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PhoneNumberNotFoundException extends Exception{

    public PhoneNumberNotFoundException(String phoneNumber){
        super("Phone number: " + phoneNumber +  " NOT FOUND.");
    }

}
