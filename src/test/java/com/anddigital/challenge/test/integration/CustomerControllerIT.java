package com.anddigital.challenge.test.integration;

import com.anddigital.challenge.ChallengeApplication;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = ChallengeApplication.class)
public class CustomerControllerIT {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void getCustomer1PhoneNumbers_ShouldReturn2PhoneNumbers() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/customer/1/phoneNumbers"),
                HttpMethod.GET, entity, String.class);

        String expected = "[\"123-456-789\",\"444-222-666\"]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:8080" + uri;
    }
}
