package com.demo.springjwt.service;

import java.util.List;
import java.util.Optional;

import com.demo.springjwt.models.Account;

public interface AccountService {
	List<Account> findAll();
	Optional<Account> findById(Long id);
	Optional<Account> findByAccountNumber(Long accountNumber);
	Account save(Account account);
}
