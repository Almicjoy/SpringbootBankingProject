package com.demo.springjwt.payload.request;

import javax.validation.constraints.NotBlank;

public class TransferRequest {

	@NotBlank
	private Long fromAccNumber;
	
	@NotBlank
	private Long toAccNumber;
	
	@NotBlank
	private Double amount;
	
	@NotBlank
	private String reason;
	
	@NotBlank
	private String byUser;
	
	public Long getFromAccNumber() {
		return fromAccNumber;
	}
	public void setFromAccNumber(Long fromAccNumber) {
		this.fromAccNumber = fromAccNumber;
	}
	public Long getToAccNumber() {
		return toAccNumber;
	}
	public void setToAccNumber(Long toAccNumber) {
		this.toAccNumber = toAccNumber;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getByUser() {
		return byUser;
	}
	public void setBy(String byUser) {
		this.byUser = byUser;
	}
	
	
}
