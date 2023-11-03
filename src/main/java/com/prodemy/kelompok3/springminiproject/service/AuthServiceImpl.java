package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;


}
