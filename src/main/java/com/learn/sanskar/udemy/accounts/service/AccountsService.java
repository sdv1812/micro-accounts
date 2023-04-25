package com.learn.sanskar.udemy.accounts.service;

import com.learn.sanskar.udemy.accounts.model.Account;
import com.learn.sanskar.udemy.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Optional<Account> getAccountByCustomerId(Integer customerId) {
        return accountsRepository.findByCustomerId(customerId);
    }
}
