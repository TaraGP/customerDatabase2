package com.example.customerDatabase1.Service;

import com.example.customerDatabase1.Entity.Customer;
import com.example.customerDatabase1.Repository.CustomerRepository;
import com.example.customerDatabase1.errorstatus.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class

CustomerService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    CustomerRepository customerRepository;

    @Value("#{${state.list}}")
    Map<String, String> stateList;

    public ResponseEntity addCustomer(Customer customer, String conversation_id)
    {
        int length = (int) (Math.log10(customer.getZip()) + 1);
        if((length==5) && (customer.getPhone().length()==10)&& (conversation_id!=null) && (customer.getZip()!=0))
        {
            int generatedBillingNumber;
            do {
                generatedBillingNumber = (int)(new Random().nextInt(999999999));
            }while(isBillingNumberExist(generatedBillingNumber));
            customer.setBillNo(generatedBillingNumber);
            //check if state has shortname or full name
            if(customer.getState().length()!=2)
            {
                if(stateList.containsKey(customer.getState()))
                {
                    customer.setState(stateList.get(customer.getState()));
                }
                else
                {
                    jmsTemplate.convertAndSend("Queue", customer.toString());
                    return new ResponseEntity<>("State doesn't exist",HttpStatus.BAD_REQUEST);
                }
            }
            else {
                if (!(stateList.containsValue(customer.getState())))
                {
                    jmsTemplate.convertAndSend("Queue", customer.toString());
                    return new ResponseEntity<>("state value doesn't exist",HttpStatus.BAD_REQUEST);
                }
            }
            customer.setConversation_id(conversation_id);
            customerRepository.save(customer);
            jmsTemplate.convertAndSend("Queue", customer.toString());
            return new ResponseEntity(customer,HttpStatus.OK);
        }
        else
        {
            jmsTemplate.convertAndSend("Queue", customer.toString());
            return new ResponseEntity<>("Pls check zip code/ phone number",HttpStatus.BAD_REQUEST);
        }
    }

    public  boolean isBillingNumberExist(int generatedBillingNumber)
    {
        if(customerRepository.existsById(generatedBillingNumber))
            return true;
        else
            return false;
    }


    public ResponseEntity getCustomer(int billingAccountNumber)
    {
      if(customerRepository.existsById(billingAccountNumber))
            return new ResponseEntity<>(customerRepository.findById(billingAccountNumber),HttpStatus.OK);
      else
          return new ResponseEntity<>("Customer not found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity getCustomerByPhoneNumber(String phoneNumber)
    {
        //Customer customer=customerRepository.findByPhone(phoneNumber);
        if(customerRepository.existsByPhone(phoneNumber))
            return new ResponseEntity<>(customerRepository.findByPhone(phoneNumber),HttpStatus.OK);
        else
            return new ResponseEntity<>("Customer not found",HttpStatus.NOT_FOUND);
    }

    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }


    public boolean deleteCustomer(int billingAccountNumber)
    {
        if(customerRepository.existsById(billingAccountNumber))
        {
            customerRepository.deleteById(billingAccountNumber);
            return true;
        }
        return false;
    }

    public boolean deleteCustomerByPhoneNumber(String PhoneNumber)
    {
        Customer customer=customerRepository.findByPhone(PhoneNumber);
        if(customer!=null)
        {
            int billingAccountNumber=customer.getBillNo();
            customerRepository.deleteById(billingAccountNumber);
            return true;
        }
        else
            return false;
    }

    public ResponseEntity updateCustomer(int billingAccountNumber,Customer customer)
    {
        if (customerRepository.existsById(billingAccountNumber))
        {
            Customer cust = customerRepository.findById(billingAccountNumber);
            if (customer.getAdd1() != null) {
                cust.setAdd1(customer.getAdd1());
            }

            if (customer.getAdd2()!= null) {
                cust.setAdd2(customer.getAdd2());
            }

            if (customer.getCity() != null) {
                cust.setCity(customer.getCity());
            }

            if (customer.getZip() != 0 ) {
                int length = (int) (Math.log10(customer.getZip()) + 1);
                if(length==5)
                    cust.setZip(customer.getZip());
            }

            if (customer.getState() != null)
            {
                if(customer.getState().length()!=2)
                {
                    if(stateList.containsKey(customer.getState()))
                    {
                        customer.setState(stateList.get(customer.getState()));
                    }
                    else
                    {
                        jmsTemplate.convertAndSend("Queue", customer.toString());
                        return new ResponseEntity<>("State doesn't exist",HttpStatus.BAD_REQUEST);
                    }
                }
                else {
                    if(stateList.containsValue(customer.getState()))
                    {
                        cust.setState(customer.getState());
                    }
                    else
                    {
                        jmsTemplate.convertAndSend("Queue", customer.toString());
                        return new ResponseEntity<>("State is not in the list",HttpStatus.BAD_REQUEST);
                    }
                }
            }
            if (customer.getEmail() != null) {
                cust.setEmail(customer.getEmail());
            }
            if(customer.getPhone()!=null){
                cust.setPhone(customer.getPhone());
            }
            customerRepository.save(cust);
            return null;
        }
        else
        {
            jmsTemplate.convertAndSend("Queue", customer.toString());
            return new ResponseEntity<>("State is not in the list",HttpStatus.NOT_FOUND);
        }
    }
}
