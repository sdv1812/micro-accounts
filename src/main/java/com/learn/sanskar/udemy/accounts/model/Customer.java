package com.learn.sanskar.udemy.accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter @Setter @ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    @NotNull
    private Integer customerId;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "create_dt")
    private LocalDate createDt;
}
