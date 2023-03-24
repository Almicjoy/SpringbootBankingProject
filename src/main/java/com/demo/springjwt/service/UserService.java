package com.demo.springjwt.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.User;
import com.demo.springjwt.models.UserQuestions;
import com.demo.springjwt.payload.request.BeneficiaryRequest;
import com.demo.springjwt.payload.request.StatusRequest;
import com.demo.springjwt.payload.response.UserResponse;

public interface UserService {
	public User save(User user);
	public User updateUserQuestions(Long userId, List<UserQuestions> questions);
	public Optional<User> findUserById(Long userId);
	public User updateUserPassword(Long userId, String password);
	public User addAccount(Long userId, Double deposit, String accountType);
	public List<Account> findUserAccounts(Long userId);
	public User addBeneficiary(Long userId, BeneficiaryRequest beneficiary);
	public void deleteBeneficiary(User user, Beneficiary beneficiary);
	public List<UserResponse> getStaff();
	public User changeStatus(StatusRequest request);
	public Optional<User> findByUsername(String username);
	public List<User> findAll();
}
