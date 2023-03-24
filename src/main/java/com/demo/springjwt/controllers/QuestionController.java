package com.demo.springjwt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.models.SecurityQuestion;
import com.demo.springjwt.models.User;
import com.demo.springjwt.models.UserQuestionPK;
import com.demo.springjwt.models.UserQuestions;
import com.demo.springjwt.service.SecurityQuestionService;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class QuestionController {
	
	@Autowired 
	SecurityQuestionService questionService;
	
	@Autowired 
	UserService userService;
	
	@GetMapping("/findAllQuestions")
	public List<SecurityQuestion> findAllQuestions(){
		return questionService.getAllSecurityQuestions();
	}
	
	@PutMapping("/saveSecurityQuestions")
	public ResponseEntity<?> saveSecurityQuestions(@RequestBody List<UserQuestions> userQuestions) {
		List<UserQuestions> questionList = questionService.prepareQuestions(userQuestions);
		
		UserQuestions userQuestion = userQuestions.iterator().next();

		Long id = userQuestion.getUser().getId();
		userService.updateUserQuestions(id, questionList);

		return ResponseEntity.ok("Questions Saved");
	}


	
}
