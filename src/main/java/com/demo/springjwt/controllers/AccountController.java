package com.demo.springjwt.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.Exceptions.NotFoundException;
import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.TransferRequest;
import com.demo.springjwt.payload.response.AccountResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.payload.response.StatementResponse;
import com.demo.springjwt.repository.AccountRepository;
import com.demo.springjwt.service.TransferService;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/customer")
public class AccountController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	TransferService transferService;
	
	@PostMapping("{userId}/account/deposit/{deposit}")
	public ResponseEntity<?> createAccount(@PathVariable Long userId, @PathVariable Double deposit, @RequestBody String accountType) {
		userService.addAccount(userId, deposit, accountType);
		return ResponseEntity.ok(new MessageResponse("Account pending approval"));
	}
	
	@GetMapping("{userId}/account")
	public List<Account> findAllUserAccounts(@PathVariable Long userId) {

		Optional<User> optUser = userService.findUserById(userId);
		if(optUser.isEmpty()) {
			throw new BadRequestException("User not found");
		}
		User user = optUser.get();
		return user.getAccounts();
	}
	
	@PutMapping("/transfer")
	public ResponseEntity<?> doTransfer(@RequestBody TransferRequest transferRequest) {
		boolean successfulTransfer = transferService.doTransfer(transferRequest);
		if (!successfulTransfer) {
			return ResponseEntity.badRequest().body(new MessageResponse("Transfer not successful"));
		}
		
		return ResponseEntity.ok().body(new MessageResponse("Transfer successful!"));
	}

	@GetMapping("/{userId}/account/{accountId}")
	public ResponseEntity<?> getAccountById(@PathVariable Long userId, @PathVariable Long accountId) {
		Optional<User> optUser = userService.findUserById(userId);
		if(optUser.isEmpty()) {
			throw new NotFoundException("User not found"); 
		}
		User user = optUser.get();
		Optional<Account> optAccount = user.getAccounts().stream()
				.filter(acc -> acc.getId().equals(accountId)).findFirst();
		if(optAccount.isEmpty()) {
			throw new NotFoundException("Account not found");
		}
		Account account = optAccount.get();
		return ResponseEntity.ok(new StatementResponse(
				account.getId(),
				account.getAccountNumber(),
				account.getAccountType().name(),
				account.getBalance(),
				account.getAccountStatus().name(),
				account.getTransactions()
		));
	}
}
