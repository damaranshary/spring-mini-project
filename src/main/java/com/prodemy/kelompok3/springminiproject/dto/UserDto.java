package com.prodemy.kelompok3.springminiproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;

    @NotEmpty
    private String username;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z ]*$")
    private String name;

    @NotEmpty
    @Email
    private String email;

    private String password;

    private String address;
}