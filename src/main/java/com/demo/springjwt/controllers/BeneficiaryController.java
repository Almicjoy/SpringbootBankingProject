package com.demo.springjwt.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.Exceptions.NotFoundException;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.BeneficiaryRequest;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.repository.BeneficiaryRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/customer")
public class BeneficiaryController {
	
	@Autowired
	UserService userService;

	@PostMapping("/{userId}/beneficiary")
	public ResponseEntity<?> addBeneficiary(@PathVariable Long userId, @RequestBody BeneficiaryRequest beneficiary) {
		userService.addBeneficiary(userId, beneficiary);
		return ResponseEntity.ok(new MessageResponse("Beneficiary pending approval"));
		
	}
	
	@GetMapping("/{userId}/beneficiary")
	public List<Beneficiary> findUserBeneficiaries(@PathVariable Long userId) {
		Optional<User> optUser = userService.findUserById(userId);
		if(optUser.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		User user = optUser.get();
		return user.getBeneficiaries();
	}
	
	@DeleteMapping("/{userId}/beneficiary/{beneficiaryId}")
	public ResponseEntity<?> deleteBeneficiary(@PathVariable Long userId, @PathVariable Long beneficiaryId) {
		Optional<User> optUser = userService.findUserById(userId);
		if(optUser.isEmpty()) {
			throw new NotFoundException("User not found");
		}
		User user = optUser.get();
		
		Optional<Beneficiary> optBeneficiary = user.getBeneficiaries().stream()
				.filter(ben -> ben.getId().equals(beneficiaryId)).findFirst();
		if(optBeneficiary.isEmpty()) {
			throw new NotFoundException("Beneficiary not found");
		}
		Beneficiary ben = optBeneficiary.get();
		userService.deleteBeneficiary(user, ben);
		return ResponseEntity.ok(new MessageResponse("Beneficiary deleted"));
	}


	
}
