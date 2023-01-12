package com.example.customerDatabase1.Service;


import com.example.customerDatabase1.Entity.Customer;
import com.example.customerDatabase1.Repository.CustomerRepository;
import com.example.customerDatabase1.errorstatus.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    CustomerRepository customerRepository;

    @Value("#{${state.list}}")
    Map<String, String> stateList;

    public ErrorStatus addCustomer(Customer customer, String conversionId)
    {

        int length = (int) (Math.log10(customer.getZip()) + 1);

        if((length==5) && (customer.getPhone().length()==10)&& (conversionId!=null) && (customer.getZip()!=0))
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
                    return new ErrorStatus(HttpStatus.BAD_REQUEST);
                }
            }
            else {
                if (!(stateList.containsValue(customer.getState())))
                {
                    jmsTemplate.convertAndSend("Queue", customer.toString());
                    return new ErrorStatus(HttpStatus.BAD_REQUEST);
                }
            }
            customer.setConversation_id(conversionId);
            customerRepository.save(customer);
            jmsTemplate.convertAndSend("Queue", customer.toString());
            return null;
        }

        else
        {
            jmsTemplate.convertAndSend("Queue", customer.toString());
            return new ErrorStatus(HttpStatus.BAD_REQUEST);
        }

    }


    public  boolean isBillingNumberExist(int generatedBillingNumber)
    {
        if(customerRepository.existsById(generatedBillingNumber))
            return true;
        else
            return false;
    }

    public void deleteCustomer(Integer billingAccountNumber)
    {
        if(customerRepository.existsById(billingAccountNumber))
        {
            customerRepository.deleteById(billingAccountNumber);
        }
    }

    public Optional<Customer> getCustomer(Integer billingAccountNumber)
    {
      if(customerRepository.existsById(billingAccountNumber))
            return Optional.ofNullable(customerRepository.findById(billingAccountNumber).get());
      else
            return null;
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber)
    {
        return customerRepository.findByPhone(phoneNumber);
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

    public ErrorStatus updateCustomer(int billingAccountNumber,Customer customer)
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
                        return  new ErrorStatus(HttpStatus.BAD_REQUEST);
                    }
                }
                else {
                    if(stateList.containsValue(customer.getState()))
                    {
                        cust.setState(customer.getState());
                    }
                    else
                    {
                        return  new ErrorStatus(HttpStatus.BAD_REQUEST);
                    }
                }
            }
            if (customer.getEmail() != null) {
                cust.setEmail(customer.getEmail());
            }
            customerRepository.save(cust);
            return null;
        }
        else
        {
            return new ErrorStatus(HttpStatus.NOT_FOUND);
        }
    }
}
