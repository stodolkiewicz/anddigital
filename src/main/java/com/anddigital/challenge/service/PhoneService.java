package com.anddigital.challenge.service;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.exception.PhoneNumberNotFoundException;
import com.anddigital.challenge.model.Phone;

import java.util.List;

public interface PhoneService {
    List<String> getAllPhoneNumbers();
    void setPhoneNumberActivation(String phoneNumber, boolean activated) throws PhoneNumberNotFoundException, DuplicatedPhoneNumberException;
    List<Phone> getAllPhones();
}
