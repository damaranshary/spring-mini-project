package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
}
