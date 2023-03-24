package com.demo.springjwt.payload.response;

import java.util.List;

import com.demo.springjwt.models.Account;

public class AccountResponse {
	private List<Account> userAccounts;

	public List<Account> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<Account> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public AccountResponse(List<Account> userAccounts) {
		this.userAccounts = userAccounts;
	}
	
}
