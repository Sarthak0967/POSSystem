package com.sarthak.POSsystem.service;

import com.sarthak.POSsystem.models.Customer;
import org.hibernate.id.enhanced.CustomOptimizerDescriptor;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer) throws  Exception;
    Customer updateCustomer(Long id, Customer customer) throws  Exception;
    void deleteCustomer(Long id) throws Exception;
    Customer getCustomerById(Long id) throws Exception;
    List<Customer> getCustomers() throws Exception;
    List<Customer> searchCustomer(String keyword) throws Exception;
}
