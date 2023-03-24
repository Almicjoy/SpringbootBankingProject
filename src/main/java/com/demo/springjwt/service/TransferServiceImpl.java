package com.demo.springjwt.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.models.Account;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.DBCR;
import com.demo.springjwt.models.Transaction;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.TransferRequest;
import com.demo.springjwt.repository.AccountRepository;
import com.demo.springjwt.repository.UserRepository;

@Service
public class TransferServiceImpl implements TransferService{
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;

	@Transactional
	@Override
	public boolean doTransfer(TransferRequest transferRequest) {
		Optional<Account> optFromAcc = checkIfExists(transferRequest.getFromAccNumber());
		Optional<Account> optToAcc = checkIfExists(transferRequest.getToAccNumber());
		Account fromAcc = optFromAcc.get();
		Account toAcc = optToAcc.get();
		if(fromAcc.getApproved().equals("no")) {
			throw new BadRequestException("Account not approved yet");
		}
		
		User user = userRepository.findByUsername(transferRequest.getByUser()).get();
		Optional<Beneficiary> optBeneficiary = user.getBeneficiaries().stream()
				.filter(b -> b.getAccountNumber().equals(transferRequest.getToAccNumber())).findFirst();
		if(optBeneficiary.isEmpty()) {
			throw new BadRequestException("Beneficiary not found");
		}
		Beneficiary beneficiary = optBeneficiary.get();
		if(beneficiary.getApproved().equals("no")) {
			throw new BadRequestException("Beneficiary not approved yet");
		}
		if(fromAcc.getBalance() < transferRequest.getAmount()) {
			throw new BadRequestException("Amount to transfer exceeds account balance");
		}
		fromAcc.setBalance((Double)fromAcc.getBalance() - (Double)transferRequest.getAmount());
		toAcc.setBalance((Double)toAcc.getBalance() + (Double)transferRequest.getAmount());
		System.out.println(fromAcc.getBalance());
		Date date = new Date();
		Transaction debit = new Transaction(
				date,
				transferRequest.getReason(),
				transferRequest.getAmount(),
				DBCR.DEBIT
		);
		Set<Transaction> debitTrans = fromAcc.getTransactions();
		if(debitTrans == null) {
			debitTrans = new HashSet<>();
		}
		debitTrans.add(debit);
		fromAcc.setTransactions(debitTrans);
		Transaction credit = new Transaction(
				date,
				transferRequest.getReason(),
				transferRequest.getAmount(),
				DBCR.CREDIT
		);
		Set<Transaction> creditTrans = toAcc.getTransactions();
		if(creditTrans == null) {
			creditTrans = new HashSet<>();
		}
		creditTrans.add(credit);
		toAcc.setTransactions(creditTrans);
		accountRepository.save(fromAcc);
		accountRepository.save(toAcc);
		return true;
	}

	private Optional<Account> checkIfExists(Long accountNumber) {
		Optional<Account> optFromAcc = accountRepository.findByAccountNumber(accountNumber);
		if(optFromAcc.isEmpty()) {
			throw new BadRequestException("From Account Not Found");
		}
		return optFromAcc;
	} 

}
