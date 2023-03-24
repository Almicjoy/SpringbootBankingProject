package com.demo.springjwt.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="securityquestions")
public class SecurityQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String question;
	
//	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//	@ElementCollection(targetClass = UserQuestions.class)
//	private List<UserQuestions> userQuestions = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	
//	public List<UserQuestions> getUserQuestions() {
//		return userQuestions;
//	}
//
//	public void setUserQuestions(List<UserQuestions> userQuestions) {
//		this.userQuestions = userQuestions;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityQuestion other = (SecurityQuestion) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "SecurityQuestion [id=" + id + ", question=" + question + "]";
	}
	
	
	

}
