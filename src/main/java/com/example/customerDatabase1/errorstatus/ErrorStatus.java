package com.example.customerDatabase1.errorstatus;

import com.example.customerDatabase1.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorStatus {
    private HttpStatus httpStatus;

}
