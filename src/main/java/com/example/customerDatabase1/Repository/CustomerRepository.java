package com.example.customerDatabase1.Repository;

import com.example.customerDatabase1.Entity.Customer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, Integer> {
    Customer findById(int billingAccountNumber);
    Customer findByPhone(String phoneNumber);
}
