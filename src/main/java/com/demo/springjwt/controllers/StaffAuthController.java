package com.demo.springjwt.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.Exceptions.ForbiddenException;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.Status;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.response.JwtResponse;
import com.demo.springjwt.service.AuthService;
import com.demo.springjwt.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/staff")
public class StaffAuthController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/authenticate")
	  public ResponseEntity<?> authenticateStaff(@Valid @RequestBody LoginRequest loginRequest) {
		Optional<User> optUser = userService.findByUsername(loginRequest.getUsername());
		if(optUser.isEmpty()) {
			throw new ForbiddenException("User not found");
		}
		User user = optUser.get();
		if(user.getStatus() == Status.DISABLE) {
			throw new ForbiddenException("User account is disabled");
		}
		Optional<Role> role = user.getRoles().stream()
			.filter(r -> r.getName().equals(ERole.ROLE_STAFF)).findFirst();
		if(role.isEmpty()) {
			throw new ForbiddenException("Cannot login as staff");
		}
		
		JwtResponse jwtResponse = authService.authenticate(loginRequest);
		return ResponseEntity.ok(jwtResponse);
		
	  }

}
