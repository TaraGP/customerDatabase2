package com.example.customerDatabase1.errorstatus;

import org.springframework.http.HttpStatus;

public class ErrorStatus {
    private HttpStatus httpStatus;
    public ErrorStatus(HttpStatus httpStatus)
    {
        this.httpStatus=httpStatus;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus)
    {
        this.httpStatus=httpStatus;
    }

}
