package com.demo.springjwt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.request.SignupRequest;
import com.demo.springjwt.payload.response.JwtResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.repository.RoleRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import com.demo.springjwt.security.services.UserDetailsImpl;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Override
	public User prepareUser(@Valid SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
		     throw new BadRequestException("Username Already Taken!") ;
		}
		
		User user = new User(signUpRequest.getUsername(), 
	               signUpRequest.getFirstname(),
	               signUpRequest.getLastname(),
	               encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = setRoles(strRoles);
		user.setRoles(roles);
		return user;
	}
	
	public Set<Role> setRoles(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
			     .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
		        switch (role) {
			        case "ROLE_STAFF":
			          Role adminRole = roleRepository.findByName(ERole.ROLE_STAFF)
			              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			          roles.add(adminRole);
			          break;
			        case "ROLE_APPROVER":
			          Role modRole = roleRepository.findByName(ERole.ROLE_APPROVER)
			              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			          roles.add(modRole);
			          break;
			        default:
			          Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
			              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			          roles.add(userRole);
		        }
			});
	    }
		return roles;
	}

	@Override
	public JwtResponse authenticate(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); 
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority()).collect(Collectors.toList());
		
		return new JwtResponse(
				jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getFirstname(), 
				userDetails.getLaststname(), roles);	
	}
	

}
