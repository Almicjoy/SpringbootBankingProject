package com.demo.springjwt.service;

import javax.validation.Valid;

import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.request.SignupRequest;
import com.demo.springjwt.payload.response.JwtResponse;

public interface AuthService {

	public User prepareUser(@Valid SignupRequest signUpRequest);
	public JwtResponse authenticate(LoginRequest loginRequest);

}
