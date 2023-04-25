package com.learn.sanskar.udemy.accounts.repository;

import com.learn.sanskar.udemy.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(int customerId);
}
