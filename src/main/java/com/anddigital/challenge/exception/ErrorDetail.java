package com.anddigital.challenge.exception;

import lombok.Data;

@Data
public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;
}
