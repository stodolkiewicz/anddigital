package com.anddigital.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DuplicatedPhoneNumberException extends Exception{

    public DuplicatedPhoneNumberException(String phoneNumber){
        super("There exist more than 1 phone number equal to: " + phoneNumber);
    }

}
