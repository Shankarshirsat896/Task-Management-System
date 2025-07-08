package com.soft.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soft.entity.UserEntity;
import com.soft.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// this method is used to retrive the user record for the Authenticatition purpose.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserName(username);
		return new User(userEntity.getUserName(), userEntity.getUserPassword(), Collections.emptyList());
	}
	
	
	// this method use for to perform user registration.
	public boolean saveUser(UserEntity user) {
		user = userRepository.save(user);
		return user.getUserId()!=null;
	}

	
	public UserEntity getUserByUsername(String username) {
	    return userRepository.findByUserName(username);
	}
	

}
