package com.prodemy.kelompok3.springminiproject.dto;

import com.prodemy.kelompok3.springminiproject.entity.User;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    String id;
    String username;
    String password;
    //String role;
	public Object setUsername;
	public UserDto(String id, String username, String password, Object setUsername) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.setUsername = setUsername;
	}
	
	
	
	
}