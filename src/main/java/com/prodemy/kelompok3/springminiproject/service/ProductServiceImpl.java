package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
}
