package com.example.customerDatabase1.Controller;


import com.example.customerDatabase1.Entity.Customer;
import com.example.customerDatabase1.Service.CustomerService;
import com.example.customerDatabase1.errorstatus.ErrorStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(value="/addCustomer", headers ="conversation_id")
    public ResponseEntity addCustomer(@Valid @RequestBody Customer customer, @RequestHeader String conversation_id)
    {
        ErrorStatus errorStatus=customerService.addCustomer(customer,conversation_id);
        if(errorStatus==null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity(errorStatus, errorStatus.getHttpStatus());
        }
    }

    @GetMapping("/getCustomer/billingAccountNumber/{billingAccountNumber}")
    public ResponseEntity getCustomer(@PathVariable int billingAccountNumber)
    {
        Optional<Customer> customer1= customerService.getCustomer(billingAccountNumber);
        if(customer1!=null)
        {
            return new ResponseEntity(customer1,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(new ErrorStatus(HttpStatus.NOT_FOUND).getHttpStatus());
        }
    }


    @GetMapping("/getCustomer/phoneNumber/{phoneNumber}")
    public ResponseEntity getCustomerByPhone(@PathVariable String phoneNumber)
    {
        Customer customer=customerService.getCustomerByPhoneNumber(phoneNumber);

        if(customer==null)
            return new ResponseEntity(new ErrorStatus(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);

        else
            return new ResponseEntity(customer,HttpStatus.OK);
    }


    @DeleteMapping("/deleteCustomer/{billingAccountNumber}")
    public ResponseEntity deleteCustomer(@PathVariable int  billingAccountNumber)
    {

        boolean operationStatus=customerService.deleteCustomer(billingAccountNumber);
        if(operationStatus)
            return new  ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(new ErrorStatus(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCustomer/phoneNumber/{phoneNumber}")
    public ResponseEntity deleteCustomerByPhoneNumber(@PathVariable String phoneNumber)
    {
        boolean operationStatus=customerService.deleteCustomerByPhoneNumber(phoneNumber);
        if(operationStatus)
            return new  ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(new ErrorStatus(HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateCustomerDetail/{billingAccountNumber}")
    public ResponseEntity updateCustomerDetails(@Valid @PathVariable int billingAccountNumber,@RequestBody Customer customer)
    {
        ErrorStatus es=customerService.updateCustomer(billingAccountNumber,customer);
        if(es==null)
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(es,es.getHttpStatus());
        }
    }
}
