package com.demo.springjwt.payload.response;

import java.util.Set;

import com.demo.springjwt.models.Transaction;

public class UserAccountResponse {
	private Long id;
	private Long accountNumber;
	private String firstname;
	private String lastname;
	private Double balance;
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Set<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	public UserAccountResponse(Long id, Long accountNumber, String firstname, String lastname, Double balance, Set<Transaction> transactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.firstname = firstname;
		this.lastname = lastname;
		this.balance = balance;
		this.transactions = transactions;
	}
	public UserAccountResponse() {
		super();
	}
	
}
