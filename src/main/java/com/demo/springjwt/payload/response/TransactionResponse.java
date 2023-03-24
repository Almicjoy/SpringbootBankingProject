package com.demo.springjwt.payload.response;

public class TransactionResponse {
	private Long id;
	private String date;
	private String reference;
	private Float amount;
	private String dbcr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getDbcr() {
		return dbcr;
	}
	public void setDbcr(String dbcr) {
		this.dbcr = dbcr;
	}
	public TransactionResponse(Long id, String date, String reference, Float amount, String dbcr) {
		super();
		this.id = id;
		this.date = date;
		this.reference = reference;
		this.amount = amount;
		this.dbcr = dbcr;
	}
}
