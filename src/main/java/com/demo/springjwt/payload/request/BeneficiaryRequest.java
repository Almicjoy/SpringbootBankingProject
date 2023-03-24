package com.demo.springjwt.payload.request;

import javax.validation.constraints.NotBlank;

public class BeneficiaryRequest {
	
	@NotBlank
	private Long accountNumber;
	@NotBlank
	private String accountType;
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
	
	

}
