package com.learn.sanskar.udemy.accounts.controller;

import com.learn.sanskar.udemy.accounts.model.Account;
import com.learn.sanskar.udemy.accounts.model.Customer;
import com.learn.sanskar.udemy.accounts.service.AccountsService;
import com.learn.sanskar.udemy.accounts.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountsController.class)
class AccountsControllerTest {

    private static final String GET_ACCOUNT_URL = "/account";
    private static final long ACCOUNT_NUMBER = 1L;
    private static final int CUSTOMER_ID = 1;
    private static final String ACCOUNT_TYPE = "atype";
    private static final String BRANCH_ADDRESS = "branchAdd";

    private static final LocalDate CREATE_DATE = LocalDate.of(2022, 2, 22);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountsService accountsService;


    @Test
    void whenCustomerIdExist_returnAccount() throws Exception {
        Account account = getAccount();
        when(accountsService.getAccountByCustomerId(anyInt())).thenReturn(Optional.of(account));
        String requestBody = """
                {
                    "customerId": 1
                }
                """;
        this.mockMvc.perform(
                        post(GET_ACCOUNT_URL)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(ACCOUNT_TYPE)))
                .andExpect(content().string(containsString(BRANCH_ADDRESS)));

    }

    @Test
    void whenCustomerIdNotExist_returnNotFoundResponse() throws Exception {
        when(accountsService.getAccountByCustomerId(anyInt())).thenReturn(Optional.empty());
        Customer customer = new Customer();
        customer.setCustomerId(2);
        this.mockMvc.perform(
                        post("/account")
                                .content(JsonUtils.asJsonString(customer))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Customer not found")));
    }

    @Test
    void givenInvalidCustomer_whenGetAccount_thenReturnBadRequest() throws Exception {
        this.mockMvc.perform(
                        post(GET_ACCOUNT_URL)
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Validation issues")));
    }

    private static Account getAccount() {
        Account account = new Account();
        account.setAccountNumber(ACCOUNT_NUMBER);
        account.setCustomerId(CUSTOMER_ID);
        account.setAccountType(ACCOUNT_TYPE);
        account.setBranchAddress(BRANCH_ADDRESS);
        account.setCreateDt(CREATE_DATE);
        return account;
    }
}