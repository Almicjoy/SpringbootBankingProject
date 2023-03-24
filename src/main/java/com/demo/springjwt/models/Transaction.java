package com.demo.springjwt.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private Date date;
	
	private String reference;
	
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	private DBCR dbcr;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public DBCR getDbcr() {
		return dbcr;
	}
	public void setDbcr(DBCR dbcr) {
		this.dbcr = dbcr;
	}
	public Transaction(Date date, String reference, Double amount, DBCR dbcr) {
		super();
		this.date = date;
		this.reference = reference;
		this.amount = amount;
		this.dbcr = dbcr;
	}
	public Transaction() {
		super();
	}
	
	
}
