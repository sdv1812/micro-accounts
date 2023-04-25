package com.learn.sanskar.udemy.accounts.controller;

import com.learn.sanskar.udemy.accounts.exception.NotFoundException;
import com.learn.sanskar.udemy.accounts.model.Account;
import com.learn.sanskar.udemy.accounts.model.Customer;
import com.learn.sanskar.udemy.accounts.repository.AccountsRepository;
import com.learn.sanskar.udemy.accounts.service.AccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class AccountsController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccountDetail(@RequestBody @Valid Customer customer) {
        Optional<Account> accountOptional = accountsService.getAccountByCustomerId(customer.getCustomerId());
        return accountOptional.orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @GetMapping(value = "/account/customer/{customerId}")
    public Account getAccount(@PathVariable("customerId") @NotNull Integer customerId)  {
        Optional<Account> accountOptional = accountsService.getAccountByCustomerId(customerId);
        return accountOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found"));
    }

}
