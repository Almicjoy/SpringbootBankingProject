package com.demo.springjwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.models.SecurityQuestion;
import com.demo.springjwt.models.User;
import com.demo.springjwt.models.UserQuestions;
import com.demo.springjwt.repository.SecurityQuestionRepository;



@Service
public class SecurityQuestionServiceImpl implements SecurityQuestionService{
	
	@Autowired
	SecurityQuestionRepository questionRepository;

	@Override
	public List<SecurityQuestion> getAllSecurityQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public List<UserQuestions> prepareQuestions(List<UserQuestions> userQuestions) {
		List<UserQuestions> questionList = new ArrayList<>();
		if(userQuestions == null) {
			throw new BadRequestException("Request failed");
		}
		for(UserQuestions question: userQuestions) { 
			User u1 = question.getUser();
			SecurityQuestion q1 = question.getQuestion();
			UserQuestions uq = new UserQuestions(u1, q1);
			uq.setAnswer(question.getAnswer());
			questionList.add(uq);
		}
		return questionList;
	}

}
