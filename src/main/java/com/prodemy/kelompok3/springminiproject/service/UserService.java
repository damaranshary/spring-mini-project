package com.prodemy.kelompok3.springminiproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;
//import com.prodemy.kelompok3.springminiproject.dto.UserRegistrationDto;
import com.prodemy.kelompok3.springminiproject.entity.User;

import jakarta.validation.Valid;

public interface UserService {

	User findByUsername(String username);

	void saveUser(@Valid UserDto userDto);

	void updateUser(UserDto userDto);

	List<UserDto> findAllUsers();
	User findUserById(String userId);

	User getCurrentUser();

	void deleteUserById(String userId);

//	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	
}
