package com.anddigital.challenge.service;

import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.exception.PhoneNumberNotFoundException;
import com.anddigital.challenge.model.Phone;
import com.anddigital.challenge.model.PhoneType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FakePhoneService implements PhoneService{

    private List<Phone> phones = new ArrayList<>();

    public FakePhoneService(){
        Phone phone1 = Phone.builder()
                .phoneNumber("123-456-789")
                .phoneType(PhoneType.LAND_LINE)
                .activated(false)
                .build();

        Phone phone2 = Phone.builder()
                .phoneNumber("444-222-666")
                .phoneType(PhoneType.MOBILE)
                .activated(false)
                .build();

        Phone phone3 = Phone.builder()
                .phoneNumber("999-678-443")
                .phoneType(PhoneType.MOBILE)
                .activated(false)
                .build();

        phones.add(phone1);
        phones.add(phone2);
        phones.add(phone3);
    }

    @Override
    public List<String> getAllPhoneNumbers(){
        List<String> phoneNumbers = phones.stream()
                .map(Phone::getPhoneNumber)
                .collect(Collectors.toList());

        return phoneNumbers;
    }

    @Override
    public Phone setPhoneNumberActivation(String phoneNumber, boolean activated) throws PhoneNumberNotFoundException, DuplicatedPhoneNumberException {
        List<Phone> phonesWithPhoneNumber = Optional.of(phones)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(phone -> phone.getPhoneNumber().equals(phoneNumber))
                .collect(Collectors.toList());

        if(phonesWithPhoneNumber.size() == 0){
            throw new PhoneNumberNotFoundException(phoneNumber);
        }else if(phonesWithPhoneNumber.size() > 1){
            throw new DuplicatedPhoneNumberException(phoneNumber);
        }else{
            phonesWithPhoneNumber.get(0).setActivated(activated);
            return phonesWithPhoneNumber.get(0);
        }
    }

    @Override
    public List<Phone> getAllPhones() {
        return this.phones;
    }

}
