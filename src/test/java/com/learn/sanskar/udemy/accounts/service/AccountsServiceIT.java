package com.learn.sanskar.udemy.accounts.service;

import com.learn.sanskar.udemy.accounts.model.Account;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class AccountsServiceIT {

    private static final int CUSTOMER_ID = 1;


    @Autowired
    private AccountsService accountsService;

    @Test
    void givenCustomerId_whenGetAccountByCustomerId_thenReturnAccount() {
        Optional<Account> optionalAccount = accountsService.getAccountByCustomerId(CUSTOMER_ID);
        assertTrue(optionalAccount.isPresent());
    }

    @Test
    void givenCustomerIdNotPresent_whenGetAccountByCustomerId_thenReturnEmptyAccount() {
        Optional<Account> optionalAccount = accountsService.getAccountByCustomerId(2);
        assertTrue(optionalAccount.isEmpty());
    }
}