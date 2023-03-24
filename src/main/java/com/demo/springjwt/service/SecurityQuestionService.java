package com.demo.springjwt.service;

import java.util.List;

import com.demo.springjwt.models.SecurityQuestion;
import com.demo.springjwt.models.UserQuestions;

public interface SecurityQuestionService {
	List<SecurityQuestion> getAllSecurityQuestions();
	List<UserQuestions> prepareQuestions(List<UserQuestions> userQuestions);
}
