package com.demo.springjwt.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springjwt.models.AadharImage;
import com.demo.springjwt.models.PanImage;
import com.demo.springjwt.models.User;
import com.demo.springjwt.payload.response.UserResponse;
import com.demo.springjwt.payload.request.UpdateProfileRequest;
import com.demo.springjwt.payload.response.MessageResponse;
import com.demo.springjwt.repository.UserRepository;
import com.demo.springjwt.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CrudController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/findAllUsers")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@GetMapping("/findUserByUsername/{username}")
	public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
		Optional<User> user = userService.findByUsername(username);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		User u = user.stream().findFirst().orElse(null);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable Long id) {
		Optional<User> optUser = userService.findUserById(id);
		if(optUser.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
		User user = optUser.stream().findFirst().get();
		return ResponseEntity.ok(new UserResponse(
				user.getId(), user.getFirstname(), user.getLastname(), 
				user.getUsername(), user.getPhone(), user.getAadhar(), user.getPan()
		));
	}
	
	@PutMapping(value = {"{id}"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> updateUserProfile(@PathVariable Long id, 
			@RequestPart("profileData") UpdateProfileRequest updateProfile,
			@RequestPart("panImage") MultipartFile[] panFile,
			@RequestPart("aadharImage") MultipartFile[] aadharFile) {
		
		Optional<User> optUser = userService.findUserById(id);
		if(optUser.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
		}
		User user = optUser.get();
		try {
			Set<AadharImage> aadharImage = uploadAadharImage(aadharFile);
			if (aadharImage != null){
				user.setAadharImage(aadharImage);
			}
			Set<PanImage> panImage = uploadPanImage(panFile);
			if(panImage != null) {
				user.setPanImage(panImage);
			}
			if(!updateProfile.getPhone().equals("")) {
				user.setPhone(updateProfile.getPhone());
			}
			if(!updateProfile.getPan().equals("")) {
				user.setPan(updateProfile.getPan());
			}
			if(!updateProfile.getAadhar().equals("")) {
				user.setAadhar(updateProfile.getAadhar());
			}
			
			userService.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new MessageResponse("User Account Updated"));
	}
	
	public Set<PanImage> uploadPanImage(MultipartFile[] multipartFiles) throws IOException{
		Set<PanImage> panImage = new HashSet<>();
		for(MultipartFile file: multipartFiles) {
			PanImage image = new PanImage(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes());
			panImage.add(image);
		}
		return panImage;
	}
	
	public Set<AadharImage> uploadAadharImage(MultipartFile[] multipartFiles) throws IOException{
		Set<AadharImage> aadharImage = new HashSet<>();
		for(MultipartFile file: multipartFiles) {
			AadharImage image = new AadharImage(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes());
			aadharImage.add(image);
		}
		return aadharImage;
	}
}
