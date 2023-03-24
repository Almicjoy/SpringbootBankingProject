package com.demo.springjwt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Beneficiary{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long accountNumber;
	
	@Enumerated(EnumType.STRING)
	private EAccount accountType;
	
	private Date dateAdded;
	
	private String approved;
	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "beneficiaries")
//	private List<User> customers;
	
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
	
	public EAccount getAccountType() {
		return accountType;
	}
	public void setAccountType(EAccount accountType) {
		this.accountType = accountType;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
//	public List<User> getCustomers() {
//		return customers;
//	}
//	public void setCustomers(List<User> customers) {
//		this.customers = customers;
//	}
	
	public Beneficiary(Long id, Long accountNumber, EAccount accountType, Date dateAdded) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.approved = "no";
		this.dateAdded = dateAdded;
	}
	public Beneficiary() {
		super();
	}
	@Override
	public String toString() {
		return "Beneficiary [id=" + id + ", accountNumber=" + accountNumber + ", accountType=" + accountType
				+ ", dateAdded=" + dateAdded + ", approved=" + approved + "]";
	}
	
}
