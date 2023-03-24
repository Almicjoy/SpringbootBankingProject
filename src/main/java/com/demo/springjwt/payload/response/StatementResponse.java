package com.demo.springjwt.payload.response;

import java.util.Set;

import com.demo.springjwt.models.Transaction;

public class StatementResponse {

	private Long id;
	private Long accountNumber;
	private String accountType;
	private Double balance;
	private String accountStatus;
	private Set<Transaction> transactions;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Set<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	public StatementResponse(Long id, Long accountNumber, String accountType, Double balance, String accountStatus,
			Set<Transaction> transactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.accountStatus = accountStatus;
		this.transactions = transactions;
	}
	
	
}
