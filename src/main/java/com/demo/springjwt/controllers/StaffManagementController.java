package com.demo.springjwt.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.Exceptions.NotFoundException;
import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.Status;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.StatusRequest;
import com.demo.springjwt.payload.request.TransferRequest;
import com.demo.springjwt.payload.response.AccountResponse;
import com.demo.springjwt.payload.response.BeneficiaryResponse;
import com.demo.springjwt.payload.response.CustomerResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.payload.response.UserAccountResponse;
import com.demo.springjwt.payload.response.UserResponse;
import com.demo.springjwt.repository.AccountRepository;
import com.demo.springjwt.repository.BeneficiaryRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.service.AccountService;
import com.demo.springjwt.service.BeneficiaryService;
import com.demo.springjwt.service.TransferService;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/staff")
public class StaffManagementController {

	@Autowired 
	TransferService transferService;
	
	@Autowired
	BeneficiaryService beneficiaryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/accounts")
	public ResponseEntity<?> getAllAccounts() {
		List<Account> accounts = accountService.findAll();
		ResponseEntity<List<Account>> res = new ResponseEntity<>(accounts, HttpStatus.OK);
		return res;
	}
	
	@PutMapping("/accounts/approve")
	public ResponseEntity<?> approveAccount(@RequestBody Long accountId) {
		Optional<Account> optAccount = accountService.findById(accountId);
		if(optAccount.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Account not found"));
		}
		Account account = optAccount.get();
		account.setApproved("yes");
		accountService.save(account);
		return ResponseEntity.ok(new MessageResponse("Account approved successfully"));
		
	}
	
	@GetMapping("/customer")
	public ResponseEntity<?> getAllCustomers() {
		List<User> users = userService.findAll();
		List<CustomerResponse> customers = users.stream()
		.filter(user -> user.getRoles().stream()
				.allMatch(role -> role.getName().equals(ERole.ROLE_CUSTOMER)))
		.map(user -> new CustomerResponse(
				user.getId(), user.getFirstname(), user.getLastname(), user.getStatus().name()))
		.collect(Collectors.toList());
		if(customers == null) {
			customers = new ArrayList<>();
		}
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
		Optional<User> optUser = userService.findUserById(customerId);
		if(optUser.isEmpty()) {
			throw new BadRequestException("User not found");
		}
		User user = optUser.get();
		return ResponseEntity.ok(new UserResponse(
				user.getId(),
				user.getFirstname(),
				user.getLastname(),
				user.getStatus().name(),
				user.getUsername()));
	}
 	
	@PutMapping("/customer")
	public ResponseEntity<?> changeCustomerStatus(@RequestBody StatusRequest request) {
		userService.changeStatus(request);
		return ResponseEntity.ok(new MessageResponse("User status changed"));
	}

	@PutMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestBody TransferRequest transfer) {
		boolean successfulTransfer = transferService.doTransfer(transfer);
		if (!successfulTransfer) {
			throw new BadRequestException("Transfer not successful");
		}
		
		return ResponseEntity.ok().body(new MessageResponse("Transfer successful"));
	}
	
	@GetMapping("/beneficiary")
	public ResponseEntity<?> getBeneficiaries() {
		List<User> users = userService.findAll();
		List<BeneficiaryResponse> beneficiaryResponse = users.stream()
			.flatMap(user -> user.getBeneficiaries().stream()
				.map(beneficiary -> new BeneficiaryResponse(
						beneficiary.getId(), beneficiary.getAccountNumber(), 
						beneficiary.getDateAdded().toLocaleString(), beneficiary.getApproved(), 
						new UserResponse(
								user.getId(), user.getFirstname(), user.getLastname(), user.getUsername()
						))))
			.toList();		
		return ResponseEntity.ok(beneficiaryResponse);
	}
	
	@PutMapping("/beneficiary")
	public ResponseEntity<?> approvebeneficiary(@RequestBody Long beneficiaryId) {
		beneficiaryService.approveBeneficiary(beneficiaryId);
		return ResponseEntity.ok(new MessageResponse("Beneficiary Account Approved"));
	}
	
	@GetMapping("/account/{accountNumber}")
	public ResponseEntity<?> getAccountByNumber(@PathVariable Long accountNumber) {
		List<User> users = userService.findAll();
		Optional<UserAccountResponse> userAccount = users.stream().flatMap(user -> user.getAccounts()
				.stream().filter(account -> account.getAccountNumber().equals(accountNumber))
				.map(account -> new UserAccountResponse(
						account.getId(), account.getAccountNumber(), user.getFirstname(),
						user.getLastname(), account.getBalance(), account.getTransactions())))
				.findFirst();
		if(userAccount.isEmpty()) {
			throw new NotFoundException("Account not found");
		}
		UserAccountResponse userAccountResponse = userAccount.get();
		return ResponseEntity.ok(userAccountResponse);
	}
	
}
