package com.prodemy.kelompok3.springminiproject.service;

import com.prodemy.kelompok3.springminiproject.entity.Product;
import com.prodemy.kelompok3.springminiproject.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {

    List<ProductImage> addImage(List<MultipartFile> files, Product product);
}
