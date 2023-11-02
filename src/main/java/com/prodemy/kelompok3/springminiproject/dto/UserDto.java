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
    String role;
}