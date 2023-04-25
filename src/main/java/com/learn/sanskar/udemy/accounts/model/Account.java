package com.learn.sanskar.udemy.accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Account {

    @Column(name = "account_number")
    @Id
    @NotNull
    private long accountNumber;
    @Column(name = "customer_id")
    @NotNull
    private int customerId;
    @Column(name = "account_type")
    @NotNull
    private String accountType;
    @Column(name = "branch_address")
    @NotNull
    private String branchAddress;
    @Column(name = "create_dt")
    @NotNull
    private LocalDate createDt;

}
