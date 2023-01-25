package com.example.customerDatabase1.Controller;

import com.example.customerDatabase1.Entity.Customer;
import com.example.customerDatabase1.Service.CustomerService;
import com.example.customerDatabase1.errorstatus.ErrorStatus;
import com.example.customerDatabase1.Repository.CustomerRepository;

import javax.validation.Valid;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@RestController
public class CustomerController  {       //used to define a controller and to indicate that the return value of the methods should be bound to the web response body.
    @Autowired
    CustomerService customerService;
    CustomerRepository customerRepository;
   @GetMapping("/signup")
    public ModelAndView showSignUpForm() {
        ModelAndView mav=new ModelAndView("CustomerList");
        mav.addObject("customers", customerService.getCustomers() );
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView showUpdateForm()
    {
        ModelAndView mav=new ModelAndView("CustomerList");
        mav.addObject("customers",customerService.getCustomers());
        return mav;
    }

    @GetMapping(value= "/addCustomer")
    public ModelAndView addCustomerForm() {
        ModelAndView mav = new ModelAndView("AddCustomer");
        Customer newCustomer = new Customer();
        mav.addObject("customer", newCustomer);
        return mav;
    }


    @PostMapping("/addCustomer")
    public ModelAndView addCustomer(@ModelAttribute("customer") Customer customer) {
        ModelAndView mav = new ModelAndView("redirect:addCustomer");
        customerService.addCustomer(customer, String.valueOf((int)Math.random()*10)+1000);
        return mav;
    }
    @GetMapping("/updateCustomer/{billNo}")
    public ModelAndView updateCustomer(@PathVariable("billNo") int billNo) {
        ModelAndView mav = new ModelAndView("UpdateCustomer");
        Customer customer=(Customer) customerService.getCustomer(billNo).getBody();
        mav.addObject("customer",customer);
        return mav;
    }

    @PostMapping("/updateCustomer/{billNo}")
    public ModelAndView updateCust(@PathVariable("billNo")int billNo,@ModelAttribute("customer")Customer customer){
        customerService.updateCustomer(billNo,customer);
        ModelAndView modelAndView=new ModelAndView("redirect:/signup");
        return modelAndView;
    }

    @GetMapping("/customers/delete/{billNo}")
    public ModelAndView deleteCust(@PathVariable("billNo") int billNo){
        customerService.deleteCustomer(billNo);
        ModelAndView mav=new ModelAndView("redirect:/signup");
        return mav;
    }


    @PostMapping(value="/addCustomer", headers ="conversation_id")
    public ResponseEntity addCustomer(@Valid @RequestBody Customer customer, @RequestHeader String conversation_id)
    {
      return customerService.addCustomer(customer,conversation_id);
    }
    @GetMapping("/getCustomer/billingAccountNumber/{billingAccountNumber}")
    public ResponseEntity getCustomer(@PathVariable int billingAccountNumber)
    {
        return customerService.getCustomer(billingAccountNumber);
    }


    @GetMapping("/getCustomer/phoneNumber/{phoneNumber}")
    public ResponseEntity getCustomerByPhone(@PathVariable String phoneNumber)
    {
        ResponseEntity responseEntity=customerService.getCustomerByPhoneNumber(phoneNumber);
        if(!(responseEntity.getStatusCode()==HttpStatus.OK))
            return new ResponseEntity(responseEntity.getBody(),HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(responseEntity,HttpStatus.OK);
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
       ResponseEntity re=customerService.updateCustomer(billingAccountNumber, customer);
        if(re==null)
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(re, re.getStatusCode());
        }
    }

}
