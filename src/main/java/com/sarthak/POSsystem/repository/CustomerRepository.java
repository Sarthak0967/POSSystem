package com.sarthak.POSsystem.repository;

import com.sarthak.POSsystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            SELECT c
            FROM Customer c
            WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
    List<Customer> searchCustomers(@Param("keyword") String keyword);
}