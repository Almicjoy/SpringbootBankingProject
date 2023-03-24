package com.demo.springjwt.payload.response;

import java.util.List;

import com.demo.springjwt.models.User;

public class BeneficiaryResponse {
	
	private Long id;
	private Long accountNumber;
	private String dateAdded;
	private String approved;
	private UserResponse customer;
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
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public UserResponse getCustomer() {
		return customer;
	}
	public void setCustomer(UserResponse customer) {
		this.customer = customer;
	}
	public BeneficiaryResponse(Long id, Long accountNumber, String dateAdded, String approved, UserResponse customer) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.dateAdded = dateAdded;
		this.approved = approved;
		this.customer = customer;
	}
	public BeneficiaryResponse() {
		super();
	}
	

}
