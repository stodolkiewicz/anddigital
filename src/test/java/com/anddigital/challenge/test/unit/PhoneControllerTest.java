package com.anddigital.challenge.test.unit;

import com.anddigital.challenge.controller.PhoneController;
import com.anddigital.challenge.exception.DuplicatedPhoneNumberException;
import com.anddigital.challenge.exception.PhoneNumberNotFoundException;
import com.anddigital.challenge.service.PhoneService;
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
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PhoneControllerTest {

    @Mock
    private PhoneService phoneService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PhoneController phoneController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        phoneController = new PhoneController();
        ReflectionTestUtils.setField(phoneController, "phoneService", phoneService);
    }

    @Test
    public void getAllPhoneNumbers_ShouldThrowNoSuchElementException_IfNoPhoneNumbersFound(){
        exception.expect(NoSuchElementException.class);

        //given
        when(phoneService.getAllPhoneNumbers()).thenReturn(new ArrayList<>());

        //when
        phoneController.getAllPhoneNumbers();
    }

    @Test
    public void getAllPhoneNumbers_ShouldReturnHttpStatusOK_IfPhoneNumbersFound(){
        //given
        List<String> mockPhoneNumbersList = getMockPhoneNumbersList();
        when(phoneService.getAllPhoneNumbers()).thenReturn(mockPhoneNumbersList);

        //when
        ResponseEntity<List<?>> allPhoneNumbers = phoneController.getAllPhoneNumbers();

        //then
        assertEquals(HttpStatus.OK, allPhoneNumbers.getStatusCode());
    }


    @Test
    public void activationOfPhoneNumber_ShouldActivatePhoneNumber() throws PhoneNumberNotFoundException, DuplicatedPhoneNumberException {
        //when
        ResponseEntity<?> responseEntity = phoneController.activationOfPhoneNumber(anyString(), anyBoolean());

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void activationOfPhoneNumber_ShouldReturnHttpStatusNotFound_IfPhoneNumberNotFoundExceptionThrown() throws PhoneNumberNotFoundException, DuplicatedPhoneNumberException {
        //given
        when(phoneService.setPhoneNumberActivation(anyString(), anyBoolean()))
                .thenThrow(PhoneNumberNotFoundException.class);

        //when
        ResponseEntity<?> responseEntity = phoneController.activationOfPhoneNumber(anyString(), anyBoolean());

        //then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void activationOfPhoneNumber_ShouldReturnHttpStatusInternalServerError_IfDuplicatedPhoneNumberExceptionThrown() throws PhoneNumberNotFoundException, DuplicatedPhoneNumberException {
        //given
        when(phoneService.setPhoneNumberActivation(anyString(), anyBoolean()))
                .thenThrow(DuplicatedPhoneNumberException.class);

        //when
        ResponseEntity<?> responseEntity = phoneController.activationOfPhoneNumber(anyString(), anyBoolean());

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }


    //mock data
    private List<String> getMockPhoneNumbersList(){
        List<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("111-222-333");
        phoneNumbers.add("222-333-444");
        return phoneNumbers;
    }

}
