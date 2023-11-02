package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;
}
