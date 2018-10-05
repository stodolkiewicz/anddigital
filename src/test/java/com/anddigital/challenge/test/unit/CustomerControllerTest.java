package com.anddigital.challenge.test.unit;

import com.anddigital.challenge.controller.CustomerController;
import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.service.CustomerService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private CustomerController customerController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController();
        ReflectionTestUtils.setField(customerController, "customerService", customerService);
    }

    @Test
    public void getCustomerPhoneNumbers_ShouldReturnHttpStatus_IfNoErrors() throws DuplicatedPhoneNumberException {
        //given
        when(customerService.getCustomerPhoneNumbers(anyLong())).thenReturn(new ArrayList<>());

        //when
        customerController.getCustomerPhoneNumbers(anyLong());
    }

    @Test
    public void getCustomerPhoneNumbers_ShouldReturnHttpStatusInternalServerError_IfDuplicatedPhoneNumberExceptionWasThrown() throws DuplicatedPhoneNumberException {
        //given
        when(customerService.getCustomerPhoneNumbers(anyLong()))
                .thenThrow(DuplicatedPhoneNumberException.class);

        //when
        ResponseEntity<?> customerPhoneNumbers = customerController.getCustomerPhoneNumbers(anyLong());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, customerPhoneNumbers.getStatusCode());
    }

}
