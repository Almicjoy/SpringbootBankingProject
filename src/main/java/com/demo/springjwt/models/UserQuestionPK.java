package com.demo.springjwt.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserQuestionPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private User user;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private SecurityQuestion question;
//	
//	
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	
//	public SecurityQuestion getQuestion() {
//		return question;
//	}
//	public void setQuestion(SecurityQuestion question) {
//		this.question = question;
//	}
//	@Override
//	public int hashCode() {
//		return Objects.hash(question, user);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserQuestionPK other = (UserQuestionPK) obj;
//		return Objects.equals(question, other.question) && Objects.equals(user, other.user);
//	}
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name= "question_id")
	private Long questionId;

	public UserQuestionPK(Long userId, Long questionId) {
		super();
		this.userId = userId;
		this.questionId = questionId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public UserQuestionPK() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(questionId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserQuestionPK other = (UserQuestionPK) obj;
		return Objects.equals(questionId, other.questionId) && Objects.equals(userId, other.userId);
	}
	
	
	

}
