package com.prodemy.kelompok3.springminiproject.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;
//import com.prodemy.kelompok3.springminiproject.dto.UserRegistrationDto;
import com.prodemy.kelompok3.springminiproject.entity.User;

import jakarta.validation.Valid;

public interface UserService {

	User save(UserDto userDto);

	User findByUsername(String username);

	void saveUser(@Valid UserDto userDto);

	List<UserDto> findAllUsers();

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	
}
