package com.demo.springjwt.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username")
    })
public class User{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  private String firstname;
  
  @NotBlank
  @Size(max = 50)
  private String lastname;

  @NotBlank
  @Size(max = 120)
  private String password;
  
  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "user_account",
		  joinColumns = @JoinColumn(name = "user_id"),
		  inverseJoinColumns = @JoinColumn(name = "account_id"))
  private List<Account> accounts = new ArrayList<>();
  
//  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_beneficiary",
		  joinColumns = @JoinColumn(name = "user_id"),
		  inverseJoinColumns = @JoinColumn(name = "beneficiary_id"))
  private List<Beneficiary> beneficiaries = new ArrayList<>();
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserQuestions> questions = null; 
  
  private String aadhar;
  
  private String pan;
  
  private String phone;
  
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_panimage",
		  joinColumns = @JoinColumn(name = "user_id"),
		  inverseJoinColumns = @JoinColumn(name = "pan_id"))
  private Set<PanImage> panImage;
  
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_aadharimage",
		  joinColumns = @JoinColumn(name = "user_id"),
		  inverseJoinColumns = @JoinColumn(name = "aadhar_id"))
  private Set<AadharImage> aadharImage;
  
  public User() {
  }

  public User(String username, String firstname, String lastname, String password) {
    this.username = username;
    this.firstname = firstname;
    this.lastname =  lastname;
    this.password = password;
    this.status = Status.ENABLE;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public void seLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<UserQuestions> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<UserQuestions> questions) {
		if(this.questions == null) {
			this.questions = questions;
		} else {
			this.questions.retainAll(questions);
			this.questions.addAll(questions);
		}
		
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<PanImage> getPanImage() {
		return panImage;
	}

	public void setPanImage(Set<PanImage> panImage) {
		this.panImage = panImage;
	}

	public Set<AadharImage> getAadharImage() {
		return aadharImage;
	}

	public void setAadharImage(Set<AadharImage> aadharImage) {
		this.aadharImage = aadharImage;
	}
	
	

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", password=" + password + "]";
	}
	
}
