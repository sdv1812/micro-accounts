package com.learn.sanskar.udemy.accounts.service;

import com.learn.sanskar.udemy.accounts.model.Account;
import com.learn.sanskar.udemy.accounts.repository.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {


    private static final long ACCOUNT_NUMBER = 1L;
    private static final int CUSTOMER_ID = 1;
    private static final String ACCOUNT_TYPE = "atype";
    private static final String BRANCH_ADDRESS = "branchAdd";
    private static final LocalDate CREATE_DATE = LocalDate.of(2022, 2, 22);

    @Mock
    private AccountsRepository accountsRepository;
    
    @InjectMocks
    private AccountsService accountsService;

    @Test
    void getAccountByCustomerId() {
        Account account = createAccount();
        when(accountsRepository.findByCustomerId(anyInt())).thenReturn(Optional.of(account));
        Optional<Account> accountOptional = accountsService.getAccountByCustomerId(CUSTOMER_ID);
        assertTrue(accountOptional.isPresent());
        assertThat(accountOptional.get().getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
        assertThat(accountOptional.get().getAccountType()).isEqualTo(ACCOUNT_TYPE);
        assertThat(accountOptional.get().getCustomerId()).isEqualTo(CUSTOMER_ID);
    }

    @Test
    void whenCustomerIdDoesNotExist_returnEmpty() {
        when(accountsRepository.findByCustomerId(anyInt())).thenReturn(Optional.empty());
        Optional<Account> accountOptional = accountsService.getAccountByCustomerId(CUSTOMER_ID);
        assertTrue(accountOptional.isEmpty());
    }

    private static Account createAccount() {
        Account account = new Account();
        account.setAccountNumber(ACCOUNT_NUMBER);
        account.setCustomerId(CUSTOMER_ID);
        account.setAccountType(ACCOUNT_TYPE);
        account.setBranchAddress(BRANCH_ADDRESS);
        account.setCreateDt(CREATE_DATE);
        return account;
    }
}