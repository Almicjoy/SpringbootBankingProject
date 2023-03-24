package com.demo.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.Exceptions.ForbiddenException;
import com.demo.springjwt.Exceptions.NotFoundException;
import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import com.demo.springjwt.models.Status;
import com.demo.springjwt.models.User;
import com.demo.springjwt.models.UserQuestions;
import com.demo.springjwt.payload.request.LoginRequest;
import com.demo.springjwt.payload.request.SignupRequest;
import com.demo.springjwt.payload.response.JwtResponse;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.repository.RoleRepository;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.security.jwt.JwtUtils;
import com.demo.springjwt.security.services.UserDetailsImpl;
import com.demo.springjwt.service.AuthService;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class AuthController {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  AuthService authService;

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) { 
	  Optional<User> optUser = userService.findByUsername(loginRequest.getUsername());
	  if(optUser.isEmpty()) {
		  throw new ForbiddenException("User not found");
	  }
	  User user = optUser.get();
	  if(user.getStatus() == Status.DISABLE) {
		  throw new ForbiddenException("User account is disabled");
	  }
	  Optional<Role> role = user.getRoles().stream()
			  .filter(r -> r.getName().equals(ERole.ROLE_CUSTOMER)).findFirst();
	  if(role.isEmpty()) {
		  throw new ForbiddenException("Cannot login as a customer");
	  }
	  JwtResponse jwtResponse = authService.authenticate(loginRequest);
	  return ResponseEntity.ok(jwtResponse);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	  User user = authService.prepareUser(signUpRequest);
	  userService.save(user);
	  return new ResponseEntity<User>(user, HttpStatus.OK);
  }
  
  @GetMapping("/{username}/forgot/{question}/{answer}")
  public ResponseEntity<?> resetPassword(@PathVariable("username") String username, 
		  @PathVariable("question") Long questionId, @PathVariable("answer") String answer) {
	  Optional<User> optUser = userService.findByUsername(username);
	  if(optUser.isEmpty()) {
		  throw new NotFoundException("User not found");
	  }
	  User user = optUser.get(); 
	  Optional<UserQuestions> userQuestion = user.getQuestions().stream()
			  .filter(question -> question.getQuestion().getId().equals(questionId)).findFirst();
	  if(userQuestion.isEmpty()) {
		  throw new BadRequestException("Error: Question is not one of user's security questions");
	  }
	  UserQuestions question = userQuestion.get();
	  if(question.getAnswer().equals(answer)) {
		  return ResponseEntity.ok().body(new MessageResponse("Success: Credentials accepted"));
	  }
	  return ResponseEntity.badRequest().body(new MessageResponse("Error: Credentials not accepted"));
  }
  
  @PostMapping("/changePassword/{username}")
  public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody String password){
	  Optional<User> user = userService.findByUsername(username);
	  if(user.isEmpty()) {
		  return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
	  }
	  User u = user.stream().findFirst().orElse(null);
	  userService.updateUserPassword(u.getId(), encoder.encode(password));
	  return ResponseEntity.ok().body(new MessageResponse("Password Updated"));
  }
}
