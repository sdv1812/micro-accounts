package com.learn.sanskar.udemy.accounts;

import com.learn.sanskar.udemy.accounts.controller.AccountsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AccountsApplicationTests {

	@Autowired
	private AccountsController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
