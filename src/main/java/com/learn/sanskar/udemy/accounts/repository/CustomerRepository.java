package com.learn.sanskar.udemy.accounts.repository;

import com.learn.sanskar.udemy.accounts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
