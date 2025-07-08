package com.soft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.dto.AuthRequest;
import com.soft.entity.UserEntity;
import com.soft.security.JwtUtils;
import com.soft.service.JwtService;
import com.soft.service.MyUserDetailsService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class UserRestController {
	
	@Autowired
	private MyUserDetailsService detailsService;
	@Autowired	
	private PasswordEncoder pwdEncoder;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private JwtUtils jwtUtils;
	
	// Registration method 
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserEntity userEntity){
		String encodedPwd = pwdEncoder.encode(userEntity.getUserPassword());
		userEntity.setUserPassword(encodedPwd);
		
		String token = jwtUtils.generateToken(userEntity.getUserName());
		userEntity.setToken(token);
		
		boolean status = detailsService.saveUser(userEntity);
		if (status) {
			return new ResponseEntity<>("User Registered..!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Registeration failed..!", HttpStatus.CONFLICT); //409 Conflict if the username already exists.
		}
	}
	
	// Login method 
	@PostMapping("/login")
	public ResponseEntity<String> userAuthentication(@RequestBody AuthRequest request){

	    UsernamePasswordAuthenticationToken token =
	            new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword());

	    try {
	        Authentication auth = authManager.authenticate(token);
	        if (auth.isAuthenticated()) {
	            String jwtToken = jwtService.generateToken(request.getUserName());

	            UserEntity userEntity = detailsService.getUserByUsername(request.getUserName());
	            userEntity.setToken(jwtToken);
	            detailsService.saveUser(userEntity); // Update the user with token

	            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return new ResponseEntity<>("Authentication failed..!", HttpStatus.BAD_REQUEST);
	}
	
	
}
