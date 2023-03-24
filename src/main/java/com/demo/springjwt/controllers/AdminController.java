package com.demo.springjwt.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springjwt.Exceptions.ForbiddenException;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.request.SignupRequest;
import com.demo.springjwt.payload.request.StatusRequest;
import com.demo.springjwt.payload.response.JwtResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.payload.response.UserResponse;
import com.demo.springjwt.service.AuthService;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/admin")
public class AdminController {
	
	@Autowired
	AuthService authService;
	
	@Autowired 
	UserService userService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
		Optional<User> optUser = userService.findByUsername(loginRequest.getUsername());
		if(optUser.isEmpty()) {
			throw new ForbiddenException("User not found");
		}
		User user = optUser.get();
		Optional<Role> role = user.getRoles().stream()
				  .filter(r -> r.getName().equals(ERole.ROLE_APPROVER)).findFirst();
		if(role.isEmpty()) {
			throw new ForbiddenException("Cannot login as admin");
		}
		JwtResponse jwtResponse = authService.authenticate(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}
	
	@PostMapping("/staff")
	public ResponseEntity<?> createStaff(@RequestBody SignupRequest signupRequest) {
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_STAFF");
		signupRequest.setRoles(roles);
		User user = authService.prepareUser(signupRequest);
		userService.save(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/staff")
	public ResponseEntity<?> getStaff() {
		List<UserResponse> staff = userService.getStaff();
		if(staff == null) {
			staff = new ArrayList<>();
		}
		return ResponseEntity.ok(staff);
	}
	
	@PutMapping("/staff")
	public ResponseEntity<?> updateStaffStatus(@RequestBody StatusRequest statusRequest) {
		userService.changeStatus(statusRequest);
		return ResponseEntity.ok(new MessageResponse("Staff status changed"));
	}

}
