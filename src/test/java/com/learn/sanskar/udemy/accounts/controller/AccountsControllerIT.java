package com.learn.sanskar.udemy.accounts.controller;

import com.learn.sanskar.udemy.accounts.model.Account;
import com.learn.sanskar.udemy.accounts.model.Customer;
import com.learn.sanskar.udemy.accounts.service.AccountsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
@SpringBootTest(webEnvironment = RANDOM_PORT)
class AccountsControllerIT {
    private static final String GET_ACCOUNT_URL = "/account";

    private static final long ACCOUNT_NUMBER = 1L;
    private static final int CUSTOMER_ID = 1;
    private static final String ACCOUNT_TYPE = "atype";
    private static final String BRANCH_ADDRESS = "branchAdd";

    private static final LocalDate CREATE_DATE = LocalDate.of(2022, 2, 22);


    @MockBean
    private AccountsService accountsService;


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void whenCustomerIdExists_returnAccount() {
        Account account = createAccount();
        when(accountsService.getAccountByCustomerId(anyInt())).thenReturn(Optional.of(account));
        Customer reqCustomer = new Customer();
        reqCustomer.setCustomerId(1);

        ResponseEntity<Account> accountResponseEntity =
                this.testRestTemplate.postForEntity(GET_ACCOUNT_URL, reqCustomer, Account.class);

        assertEquals(HttpStatus.OK.value(), accountResponseEntity.getStatusCode().value());
        assertThat(accountResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(accountResponseEntity.getBody());
        assertThat(accountResponseEntity.getBody().getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
        assertThat(accountResponseEntity.getBody().getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(accountResponseEntity.getBody().getAccountType()).isEqualTo(ACCOUNT_TYPE);
        assertThat(accountResponseEntity.getBody().getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
        assertThat(accountResponseEntity.getBody().getCreateDt()).isEqualTo(CREATE_DATE);
    }

    @Test
    void whenCustomerIdNotFound_returnNotFoundResponse() throws JSONException {
        when(accountsService.getAccountByCustomerId(anyInt())).thenReturn(Optional.empty());
        Customer reqCustomer = new Customer();
        reqCustomer.setCustomerId(1);

        ResponseEntity<String> accountResponseEntity =
                this.testRestTemplate.postForEntity(GET_ACCOUNT_URL, reqCustomer, String.class);

        assertEquals(HttpStatus.NOT_FOUND.value(), accountResponseEntity.getStatusCode().value());
        assertThat(accountResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        JSONObject jsonObject = new JSONObject(accountResponseEntity.getBody());
        assertEquals("Not Found", jsonObject.getString("error"));
    }

    @Test
    void givenInvalidCustomer_whenGetAccount_thenReturnBadRequest() {
        //given
        Customer invalidCustomer = new Customer();
        //when
        ResponseEntity<String> accountResponseEntity = this.testRestTemplate
                .postForEntity(GET_ACCOUNT_URL, invalidCustomer, String.class);
        //then
        assertThat(accountResponseEntity.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(accountResponseEntity.getBody()).isNotNull();
        assertThat(accountResponseEntity.getBody()).contains("Bad Request");
        assertThat(accountResponseEntity.getBody()).contains("Validation issues. Check in 'errors' field in the response.");
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