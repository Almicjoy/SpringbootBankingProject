package com.demo.springjwt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.Exceptions.NotFoundException;
import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.EAccount;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.Status;
import com.demo.springjwt.models.User;
import com.demo.springjwt.models.UserQuestions;
import com.demo.springjwt.payload.request.BeneficiaryRequest;
import com.demo.springjwt.payload.request.StatusRequest;
import com.demo.springjwt.payload.response.CustomerResponse;
import com.demo.springjwt.payload.response.UserResponse;
import com.demo.springjwt.repository.AccountRepository;
import com.demo.springjwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	String bankCode = "80092";

	@Override
	public User updateUserQuestions(Long userId, List<UserQuestions> questions) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		
		User userUpdates = user.get();
		userUpdates.setQuestions(questions);
		return userRepository.save(userUpdates);
	}
	
	@Override
	public User updateUserPassword(Long userId, String password) {
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		User user = optUser.get();
		user.setPassword(password);
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User addAccount(Long userId, Double deposit, String accountType) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		User userUpdates = user.stream().findFirst().orElse(null);
		
		Optional<Account> accountWithNumber;
		Long accountNumber;
		do {
			Random rand = new Random(); 
	        int n = rand.nextInt((int) Math.pow(10, 10));
	        String nString = String.format("%d", n);
	        String accountNum = bankCode + nString;
	        accountNumber = Long.parseLong(accountNum);
	        accountWithNumber = accountRepository.findByAccountNumber(accountNumber);
		}
		
        while(!accountWithNumber.isEmpty());
        
        Account account = new Account();
        
        account.setAccountNumber(accountNumber);
        account.setApproved("no");
        account.setBalance(deposit);
        account.setAccountStatus(Status.ENABLE);
        if(accountType.equals("checking")) {
        	account.setAccountType(EAccount.ACCOUNT_CHECKING);
        } else {
        	account.setAccountType(EAccount.ACCOUNT_SAVING);
        }
        
        List<Account> accountList = new ArrayList<>();
        if(userUpdates.getAccounts() == null) {
        	accountList.add(account);
        } else {
        	accountList = userUpdates.getAccounts();
        	accountList.add(account);
        }
		return userRepository.save(userUpdates);
	}

	@Override
	public List<Account> findUserAccounts(Long userId) {
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			return null;
		}
		User user = optUser.stream().findFirst().orElse(null);
		List<Account> userAccounts = user.getAccounts();
		System.out.println(user);
		return userAccounts;
	}

	@Override
	public User addBeneficiary(Long userId, BeneficiaryRequest beneficiary) {
		Optional<User> optUser = userRepository.findById(userId);
		if(optUser.isEmpty()) {
			throw new BadRequestException("User not found. Beneficiary not added.");
		}
		User user = optUser.get();
		
		Optional<Account> optAccount = accountRepository.findByAccountNumber(beneficiary.getAccountNumber());
		if(optAccount.isEmpty()) {
			throw new BadRequestException("Account not found. Beneficiary not added.");
		}
		
		Beneficiary ben = prepareBeneficiary(beneficiary);
		List<Beneficiary> beneficiaryList = addToBeneficiaryList(user, ben);
		user.setBeneficiaries(beneficiaryList);
		return userRepository.save(user);
	}

	private Beneficiary prepareBeneficiary(BeneficiaryRequest beneficiary) {
		Beneficiary ben = new Beneficiary();
		ben.setAccountNumber(beneficiary.getAccountNumber());
		ben.setApproved("no");
		ben.setDateAdded(new Date());
		if(beneficiary.getAccountType().equals("checking")) {
			ben.setAccountType(EAccount.ACCOUNT_CHECKING);
		} else {
			ben.setAccountType(EAccount.ACCOUNT_SAVING);
		}
		return ben;
	}
	
	private List<Beneficiary> addToBeneficiaryList(User user, Beneficiary ben) {
		List<Beneficiary> beneficiaryList = new ArrayList<>();
		if(user.getBeneficiaries() == null) {
			beneficiaryList.add(ben);
		} else {
			beneficiaryList = user.getBeneficiaries();
			beneficiaryList.add(ben);
		}
		return beneficiaryList;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<UserResponse> getStaff() {
		List<User> users = userRepository.findAll();
		List<UserResponse> staff = users.stream()
				.filter(user -> user.getRoles().stream()
						.allMatch(role -> role.getName().equals(ERole.ROLE_STAFF)))
				.map(user -> new UserResponse(
						user.getId(), user.getFirstname(), user.getLastname(), 
						user.getStatus().name(), user.getUsername()))
				.collect(Collectors.toList());
		return staff;
	}

	@Override
	public User changeStatus(StatusRequest request) {
		Optional<User> optUser = userRepository.findById(request.getId());
		if(optUser.isEmpty()) {
			throw new BadRequestException("User account not found");
		}
		User user = optUser.get();
		
		if(request.getStatus().equals("ENABLE")) {
			user.setStatus(Status.ENABLE);
		} else if (request.getStatus().equals("DISABLE")) {
			user.setStatus(Status.DISABLE);
		} else {
			
		}
		return userRepository.save(user);
	}

	@Override
	public void deleteBeneficiary(User user, Beneficiary beneficiary) {
		List<Beneficiary> beneficiaries = user.getBeneficiaries();
		beneficiaries.remove(beneficiary);
		user.setBeneficiaries(beneficiaries);
		userRepository.save(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	

}
