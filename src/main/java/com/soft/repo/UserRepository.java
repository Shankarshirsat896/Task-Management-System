package com.soft.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soft.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long>{

	// select * from user_entity where userName=:userName
	public UserEntity findByUserName(String userName);
}
