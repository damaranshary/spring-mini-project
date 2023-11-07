package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.dto.UserDto;

import com.prodemy.kelompok3.springminiproject.entity.Role;
import com.prodemy.kelompok3.springminiproject.entity.User;
import com.prodemy.kelompok3.springminiproject.repository.RoleRepository;
import com.prodemy.kelompok3.springminiproject.repository.UserRepository;

//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;

    /*
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
    */

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setCart(cartService.initializeCartForUser(user));

        Role role = roleRepository.findByName("ROLE_USER");

        if(role == null){
            role = assignUserRole();
        }

        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());

        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        return userDto;
    }

    private Role assignAdminRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    private Role assignUserRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @Override
    public User getCurrentUser() {
        // Get the authentication object from the SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            // If the principal is a UserDetails object, it's a user in the system
            // You may have a User object associated with the UserDetails
            // Retrieve the User object using your own method or repository
            return findByUsername(userDetails.getUsername());
        } else {
            // Handle the case where the principal is not a UserDetails object
            // This can happen for anonymous users or other scenarios
            // You can return null or throw an exception based on your requirements
            return null;
        }
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
/*
    private User getUserByUsername(String username) {
        // Implement a method to fetch the User by username from your database or repository
        // Return the User entity
    }

	*/
}

