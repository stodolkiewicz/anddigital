package com.anddigital.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long customerId;
    private String firstName;
    private String lastName;
    private String primaryAddress;
    private String email;
    private List<Phone> phoneList;

}
