package com.anddigital.challenge.service;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.model.Customer;
import com.anddigital.challenge.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FakeCustomerService implements CustomerService{

    FakePhoneService fakePhoneService;

    private List<Phone> phones;
    private List<Customer> customers;

    @Autowired
    public FakeCustomerService(FakePhoneService fakePhoneService){
        this.fakePhoneService = fakePhoneService;
        phones = fakePhoneService.getAllPhones();
        customers = new ArrayList<>();

        List<Phone> firstCustomerPhones = new ArrayList<>();
        firstCustomerPhones.add(phones.get(0));
        firstCustomerPhones.add(phones.get(1));

        Customer customer1 =
                new Customer().builder()
                        .customerId(1L)
                        .firstName("John")
                        .lastName("Smith")
                        .email("john.smith@gmail.com")
                        .phoneList(firstCustomerPhones)
                        .build();

        List<Phone> secondCustomerPhones = new ArrayList<>();
        secondCustomerPhones.add(phones.get(2));

        Customer customer2 =
                new Customer().builder()
                        .customerId(2L)
                        .firstName("Andrew")
                        .lastName("Dow")
                        .email("andrew.doe@mail.com")
                        .phoneList(secondCustomerPhones)
                        .build();

        customers.add(customer1);
        customers.add(customer2);
    }

    @Override
    public List<String> getCustomerPhoneNumbers(Long customerId) throws DuplicatedPhoneNumberException {
        List<List<String>> customerPhonesList = customers.stream()
                .filter(customer -> Objects.equals(customer.getCustomerId(), customerId))
                .map(this::getCustomerPhoneList)
                .collect(Collectors.toList());

        if(customerPhonesList.size() == 0){
            throw new NoSuchElementException("No phone Numbers found for customer with id: " + customerId);
        }else if(customerPhonesList.size() > 1){
            throw new DuplicatedPhoneNumberException("Customer with id: " + customerId + " has a duplicated phone number with another customer.");
        }else{
            return customerPhonesList.get(0);
        }
    }

    private List<String> getCustomerPhoneList(Customer customer) {
        List<Phone> phoneList = customer.getPhoneList();
        return phoneList.stream()
                .map(Phone::getPhoneNumber)
                .collect(Collectors.toList());
    }

}
