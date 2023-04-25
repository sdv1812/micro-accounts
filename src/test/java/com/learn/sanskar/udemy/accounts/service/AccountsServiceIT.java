package com.learn.sanskar.udemy.accounts.service;

import com.learn.sanskar.udemy.accounts.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class AccountsServiceIT {

    @MockBean
    private AccountsRepository accountsRepository;

    @Test
    void getAccountByCustomerId() {
    }
}