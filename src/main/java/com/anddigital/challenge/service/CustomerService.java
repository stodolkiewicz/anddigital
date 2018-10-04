package com.anddigital.challenge.service;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;

import java.util.List;

public interface CustomerService {
    List<String> getCustomerPhoneNumbers(Long customerId) throws DuplicatedPhoneNumberException;
}
