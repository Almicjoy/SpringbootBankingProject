package com.demo.springjwt.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_questions")

public class UserQuestions implements Serializable{
	

	@EmbeddedId
	private UserQuestionPK userQuestionPK;
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questionId")
    private SecurityQuestion question;
	
    @Column(name = "answer")
	private String answer;
    
    public UserQuestions(User user, SecurityQuestion question) {
    	this.user = user;
    	this.question = question;
    	this.userQuestionPK = new UserQuestionPK(user.getId(), question.getId());
    }

	public UserQuestions() {
		super();
	}

	public UserQuestionPK getUserQuestionPK() {
		return userQuestionPK;
	}

	public void setUserQuestionPK(UserQuestionPK userQuestionPK) {
		this.userQuestionPK = userQuestionPK;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public User getUser() {
		return this.user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public SecurityQuestion getQuestion() {
		return this.question;
	}
	public void setSecurityQuestion(SecurityQuestion question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "UserQuestions [question=" + question + ", answer=" + answer + "]\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(question, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserQuestions other = (UserQuestions) obj;
		return Objects.equals(question, other.question)
				&& Objects.equals(user, other.user);
	}
}
