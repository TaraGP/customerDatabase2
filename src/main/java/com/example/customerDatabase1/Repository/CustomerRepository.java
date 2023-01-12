package com.example.customerDatabase1.Repository;

import com.example.customerDatabase1.Entity.Customer;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findById(int billingAccountNumber);
    Customer findByPhone(String phoneNumber);

   /* boolean existsById(Integer billingAccountNumber);

    void deleteById(Integer billingAccountNumber);

    void save(Customer cust);

    List<Customer> findAll();*/
}
